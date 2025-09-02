package com.dragn0007.hhamsters.entities.hamster;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.util.LOAnimations;
import com.dragn0007.dragnlivestock.items.LOItems;
import com.dragn0007.dragnlivestock.util.LOTags;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import com.dragn0007.hhamsters.HamtasticHamsters;
import com.dragn0007.hhamsters.entities.ai.HamsterFollowOwnerGoal;
import com.dragn0007.hhamsters.entities.util.EntityTypes;
import com.dragn0007.hhamsters.gui.HamsterMenu;
import com.dragn0007.hhamsters.util.HHTags;
import com.dragn0007.hhamsters.util.HamtasticHamstersCommonConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Hamster extends TamableAnimal implements InventoryCarrier, GeoEntity, ContainerListener {

	public Hamster(EntityType<? extends Hamster> type, Level level) {
		super(type, level);
		this.setTame(false);
		this.updateInventory();
		this.setCanPickUpLoot(true);
	}

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0D, (double)this.getEyeHeight() * 0.6F, (double)(this.getBbWidth() * 0.6F));
		//              ^ Side offset                      ^ Height offset                   ^ Length offset
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 2.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.17F)
				.add(Attributes.ATTACK_DAMAGE, 0.5D);
	}

	public static final Ingredient FOOD_ITEMS = Ingredient.of(HHTags.Items.HAMSTER_FOOD);

	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}

	public void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.4, true));
		this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(5, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 2.0F));
		this.goalSelector.addGoal(3, new HamsterSearchForItemsGoal());
		this.goalSelector.addGoal(3, new Hamster.PickCropsGoal(this));

		this.goalSelector.addGoal(6, new HamsterFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, livingEntity ->
				livingEntity.getType().is(LOTags.Entity_Types.WOLVES) && (livingEntity instanceof TamableAnimal && !((TamableAnimal) livingEntity).isTame()) && !this.isTame()
		));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, livingEntity ->
				livingEntity.getType().is(LOTags.Entity_Types.CATS) && (livingEntity instanceof TamableAnimal && !((TamableAnimal) livingEntity).isTame()) && !this.isTame()
		));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, livingEntity ->
				livingEntity.getType().is(LOTags.Entity_Types.DOGS) && (livingEntity instanceof TamableAnimal && !((TamableAnimal) livingEntity).isTame()) && !this.isTame()
		));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, livingEntity ->
				livingEntity.getType().is(LOTags.Entity_Types.FOXES) && (livingEntity instanceof TamableAnimal && !((TamableAnimal) livingEntity).isTame()) && !this.isTame()
		));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, livingEntity ->
				livingEntity instanceof PolarBear && !this.isTame()
		));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, livingEntity ->
				livingEntity instanceof Panda && !this.isTame()
		));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, livingEntity ->
				livingEntity instanceof Villager && !this.isTame()
		));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, livingEntity ->
				livingEntity instanceof Player && !this.isTame() && livingEntity.isCrouching()
		));

		this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 2, true, false,
				entity -> entity instanceof Hamster && HamtasticHamstersCommonConfig.HAMSTERS_FIGHT.get() && this.isTame() && !this.isBaby() && !entity.isBaby() && ((Hamster) entity).isTame() && ((Hamster) entity).getGender() == this.getGender()));
	}

	public boolean hurt(DamageSource damageSource, float amount) {
		if (damageSource.getEntity() instanceof Player player) {

			if(!this.level().isClientSide && this.isTame() && !this.isOrderedToSit() && !this.isInSittingPose() && !this.wasToldToWander())
			{
				if (this.isOwnedBy(player) && player.isShiftKeyDown()) {
					this.setToldToWander(true);
					player.displayClientMessage(Component.translatable("tooltip.hhamsters.wandering.tooltip").withStyle(ChatFormatting.GOLD), true);
				}
				return false;
			}

			if (!this.level().isClientSide && this.isTame() && !this.isOrderedToSit() && !this.isInSittingPose() && this.wasToldToWander()) {
				if (this.isOwnedBy(player) && player.isShiftKeyDown()) {
					this.setToldToWander(false);
					player.displayClientMessage(Component.translatable("tooltip.hhamsters.following.tooltip").withStyle(ChatFormatting.GOLD), true);
				}
				return false;
			}
		}
		return super.hurt(damageSource, amount);
	}

	public int fedCounter = 0;
	public int standCounter = this.random.nextInt(3600) + 3600;
	public int stayStandingCounter = 0;

	@Override
	public void tick() {
		super.tick();

		fedCounter++;

		if (fedCounter >= 2400) {
			this.setFed(false);
		}

		standCounter--;

		if (isStanding()) {
			navigation.stop();
		}

		if (--this.standCounter <= 0) {
			this.stayStandingCounter++;
			setStanding(true);
		    if (this.stayStandingCounter >= 150) {
				this.standCounter = this.random.nextInt(3600) + 3600;
				this.stayStandingCounter = 0;
				setStanding(false);
			}
		}

	}

	private boolean fed = false;
	public boolean isFed() {
		return this.fed;
	}
	public boolean getPlantsSheared() {
		return this.fed;
	}
	public void setFed(boolean fed) {
		this.fed = fed;
	}

	private boolean stand = false;
	public boolean isStanding() {
		return this.stand;
	}
	public void setStanding(boolean standing) {
		this.stand = standing;
	}

	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		Item item = itemstack.getItem();

		if (itemstack.is(LOItems.GENDER_TEST_STRIP.get()) && this.isFemale()) {
			player.playSound(SoundEvents.BEEHIVE_EXIT, 1.0F, 1.0F);
			ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, LOItems.FEMALE_GENDER_TEST_STRIP.get().getDefaultInstance());
			player.setItemInHand(hand, itemstack1);
			return InteractionResult.SUCCESS;
		}

		if (itemstack.is(LOItems.GENDER_TEST_STRIP.get()) && this.isMale()) {
			player.playSound(SoundEvents.BEEHIVE_EXIT, 1.0F, 1.0F);
			ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, LOItems.MALE_GENDER_TEST_STRIP.get().getDefaultInstance());
			player.setItemInHand(hand, itemstack1);
			return InteractionResult.SUCCESS;
		}

		if (this.isOwnedBy(player) && this.isTame() && !this.isBaby()) {
			if (this.isTame() && player.isSecondaryUseActive() && player.isCrouching()) {
				this.openInventory(player);
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
		}

		if (this.isTame()) {
			if (this.isFood(itemstack)) {
				this.setFed(true);
				fedCounter = 0;
				this.level().addParticle(ParticleTypes.HEART, this.getRandomX(0.6D), this.getRandomY(), this.getRandomZ(0.6D), 0.7D, 0.7D, 0.7D);

				if (this.getHealth() < this.getMaxHealth()) {
					this.heal((float) 2.0);
				}

				int i = this.getAge();
				if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
					this.usePlayerItem(player, hand, itemstack);
					this.setInLove(player);
					return InteractionResult.SUCCESS;
				}

				if (!player.getAbilities().instabuild) {
					itemstack.shrink(1);
				}

				this.gameEvent(GameEvent.ENTITY_INTERACT);
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
		}

		if (this.level().isClientSide) {
			boolean flag = this.isOwnedBy(player) || this.isTame() || this.isFood(itemstack) && !this.isTame();
			return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
		} else {
			if (this.isTame()) {
				if (this.isFood(itemstack)) {

					if (this.getHealth() < this.getMaxHealth()) {
						this.heal((float) itemstack.getFoodProperties(this).getNutrition());
					}

					if (!player.getAbilities().instabuild) {
						itemstack.shrink(1);
					}

					int i = this.getAge();
					if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
						this.usePlayerItem(player, hand, itemstack);
						this.setInLove(player);
						return InteractionResult.SUCCESS;
					}

					return InteractionResult.SUCCESS;
				}

				if (!this.isFood(itemstack)) {
					InteractionResult interactionresult = super.mobInteract(player, hand);
					if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(player)) {
						this.setOrderedToSit(!this.isOrderedToSit());
						this.jumping = false;
						this.navigation.stop();
						this.setTarget((LivingEntity)null);
						return InteractionResult.SUCCESS;
					}

					return interactionresult;
				}

			} else if (this.isFood(itemstack)) {
				if (!player.getAbilities().instabuild) {
					itemstack.shrink(1);
				}

				if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
					this.tame(player);
					this.navigation.stop();
					this.setTarget((LivingEntity)null);
					this.setOrderedToSit(true);
					this.level().broadcastEntityEvent(this, (byte)7);
				} else {
					this.level().broadcastEntityEvent(this, (byte)6);
				}

				return InteractionResult.SUCCESS;
			}

			return super.mobInteract(player, hand);
		}
	}

	public boolean toldToWander = false;

	public boolean wasToldToWander() {
		return this.toldToWander;
	}

	public boolean getToldToWander() {
		return this.toldToWander;
	}

	public void setToldToWander(boolean toldToWander) {
		this.toldToWander = toldToWander;
	}

	@Override
	public float getStepHeight() {
		return 1.0F;
	}

	private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	private <T extends GeoAnimatable> PlayState predicate(software.bernie.geckolib.core.animation.AnimationState<T> tAnimationState) {
		double currentSpeed = this.getDeltaMovement().lengthSqr();
		double speedThreshold = 0.01;

		AnimationController<T> controller = tAnimationState.getController();

		if (tAnimationState.isMoving()) {
			if (currentSpeed > speedThreshold) {
				controller.setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
				controller.setAnimationSpeed(2.0);
			} else {
				controller.setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
				controller.setAnimationSpeed(1.2);
			}
		} else if (this.isStanding()) {
			controller.setAnimation(RawAnimation.begin().then("stand", Animation.LoopType.LOOP));
			controller.setAnimationSpeed(0.8);
		} else if (this.isInSittingPose()) {
			controller.setAnimation(RawAnimation.begin().then("sploot", Animation.LoopType.LOOP));
			controller.setAnimationSpeed(0.8);
		} else {
			controller.setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
			controller.setAnimationSpeed(0.8);
		}

		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 2, this::predicate));
		controllers.add(LOAnimations.genericAttackAnimation(this, LOAnimations.ATTACK));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.geoCache;
	}

	int moreCropsTicks;

	static class PickCropsGoal extends MoveToBlockGoal {
		public final Hamster hamster;
		public boolean wantsToPick;
		public boolean canPick;

		public PickCropsGoal(Hamster hamster) {
			super(hamster, 0.7F, 16);
			this.hamster = hamster;
		}

		public boolean canUse() {
			if (this.nextStartTick <= 0) {
				if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.hamster.level(), this.hamster)) {
					return false;
				}

				this.canPick = false;
				this.wantsToPick = true;
			}

			return super.canUse();
		}

		public boolean canContinueToUse() {
			return this.canPick && super.canContinueToUse();
		}

		public static Property<Integer> getCropProperty(BlockState state, String propertyName) {
			Block block = state.getBlock();
			if (block instanceof CropBlock) {
				for (Property<?> prop : state.getProperties()) {
					if (prop instanceof IntegerProperty && prop.getName().equals(propertyName)) {
						return (Property<Integer>) prop;
					}
				}
			}
			return null;
		}

		public void tick() {
			super.tick();

			this.hamster.getLookControl().setLookAt(
					(double)this.blockPos.getX() + 0.5D,
					this.blockPos.getY() + 1,
					(double)this.blockPos.getZ() + 0.5D, 10.0F,
					(float)this.hamster.getMaxHeadXRot());

			if (this.isReachedTarget()) {
				Level level = this.hamster.level();
				BlockPos blockpos = this.blockPos.above();
				BlockState blockstate = level.getBlockState(blockpos);
				Block block = blockstate.getBlock();
				Property<Integer> age = getCropProperty(blockstate, "age");

				if (this.canPick && block instanceof CropBlock) {
					blockstate.getBlock().getDrops(blockstate, (ServerLevel) level, blockpos, null).forEach
							(stack -> level.addFreshEntity(new ItemEntity(level,
									blockpos.getX() + 0.5,
									blockpos.getY() + 0.5,
									blockpos.getZ() + 0.5, stack)));

					level.setBlockAndUpdate(blockpos, blockstate.setValue(age, Integer.valueOf(1)));
					level.levelEvent(2001, blockpos, Block.getId(blockstate));
				}

				this.hamster.moreCropsTicks = 40;
				this.canPick = false;
				this.nextStartTick = 20;
			}

		}

		public boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
			BlockState blockstate = levelReader.getBlockState(blockPos);
			if (blockstate.is(Blocks.FARMLAND) && this.wantsToPick && !this.canPick) {
				blockstate = levelReader.getBlockState(blockPos.above());
				if (blockstate.getBlock() instanceof CropBlock && ((CropBlock)blockstate.getBlock()).isMaxAge(blockstate)) {
					this.canPick = true;
					return true;
				}
			}

			return false;
		}
	}

	public SoundEvent getAmbientSound() {
		super.getAmbientSound();
		return SoundEvents.RABBIT_AMBIENT;
	}

	public SoundEvent getDeathSound() {
		super.getDeathSound();
		return SoundEvents.RABBIT_DEATH;
	}

	public SoundEvent getHurtSound(DamageSource p_30720_) {
		super.getHurtSound(p_30720_);
		return SoundEvents.RABBIT_HURT;
	}

	public void playStepSound(BlockPos p_28254_, BlockState p_28255_) {
		this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
	}

	// Generates the base texture
	public static final EntityDataAccessor<String> VARIANT_TEXTURE = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> OVERLAY_TEXTURE = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.STRING);

	public String getTextureResource() {
		return this.entityData.get(VARIANT_TEXTURE);
	}

	public String getOverlayLocation() {
		return this.entityData.get(OVERLAY_TEXTURE);
	}

	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> OVERLAY = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> BREED = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.INT);

	public int getVariant() {
		return this.entityData.get(VARIANT);
	}
	public int getOverlayVariant() {
		return this.entityData.get(OVERLAY);
	}
	public int getBreed() {
		return this.entityData.get(BREED);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
		this.entityData.set(VARIANT_TEXTURE, HamsterModel.Variant.variantFromOrdinal(variant).resourceLocation.toString());
	}
	public void setOverlayVariant(int overlayVariant) {
		this.entityData.set(OVERLAY, overlayVariant);
		this.entityData.set(OVERLAY_TEXTURE, HamsterMarkingLayer.Overlay.patternFromOrdinal(overlayVariant).resourceLocation.toString());
	}

	public void setVariantTexture(String variant) {
		this.entityData.set(VARIANT_TEXTURE, variant);
	}

	public void setOverlayVariantTexture(String variant) {
		this.entityData.set(OVERLAY_TEXTURE, variant);
	}

	public void setBreed(int breed) {
		this.entityData.set(BREED, breed);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		if (tag.contains("Variant")) {
			setVariant(tag.getInt("Variant"));
		}

		if (tag.contains("Overlay")) {
			setOverlayVariant(tag.getInt("Overlay"));
		}

		if (tag.contains("Variant_Texture")) {
			this.setVariantTexture(tag.getString("Variant_Texture"));
		}

		if (tag.contains("Overlay_Texture")) {
			this.setOverlayVariantTexture(tag.getString("Overlay_Texture"));
		}

		if (tag.contains("Gender")) {
			this.setGender(tag.getInt("Gender"));
		}

		if (tag.contains("Breed")) {
			this.setBreed(tag.getInt("Breed"));
		}

		if (tag.contains("Wandering")) {
			this.setToldToWander(tag.getBoolean("Wandering"));
		}

		if (tag.contains("Fed")) {
			this.setFed(tag.getBoolean("Fed"));
		}

		this.updateInventory();

		if(this.isTame()) {
			ListTag listTag = tag.getList("Items", 10);

			for(int i = 0; i < listTag.size(); i++) {
				CompoundTag compoundTag = listTag.getCompound(i);
				int j = compoundTag.getByte("Slot") & 255;
				if(j < this.inventory.getContainerSize()) {
					this.inventory.setItem(j, ItemStack.of(compoundTag));
				}
			}
		}

		this.setCanPickUpLoot(true);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", getVariant());

		tag.putInt("Overlay", getOverlayVariant());

		tag.putString("Variant_Texture", this.getTextureResource().toString());

		tag.putString("Overlay_Texture", this.getOverlayLocation().toString());

		tag.putInt("Gender", this.getGender());

		tag.putInt("Breed", this.getBreed());

		tag.putBoolean("Wandering", this.getToldToWander());

		tag.putBoolean("Fed", this.getPlantsSheared());

		if(this.isTame()) {
			ListTag listTag = new ListTag();

			for(int i = 0; i < this.inventory.getContainerSize(); i++) {
				ItemStack itemStack = this.inventory.getItem(i);
				if(!itemStack.isEmpty()) {
					CompoundTag compoundTag = new CompoundTag();
					compoundTag.putByte("Slot", (byte) i);
					itemStack.save(compoundTag);
					listTag.add(compoundTag);
				}
			}
			tag.put("Items", listTag);
		}

	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
		if (data == null) {
			data = new AgeableMobGroupData(0.2F);
		}
		Random random = new Random();
		setVariant(random.nextInt(HamsterModel.Variant.values().length));
		setOverlayVariant(random.nextInt(HamsterMarkingLayer.Overlay.values().length));
		this.setGender(random.nextInt(Gender.values().length));
		this.setBreed(random.nextInt(Breed.values().length));

		return super.finalizeSpawn(serverLevelAccessor, instance, spawnType, data, tag);
	}

	@Override
	public void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
		this.entityData.define(OVERLAY, 0);
		this.entityData.define(GENDER, 0);
		this.entityData.define(BREED, 0);
		this.entityData.define(VARIANT_TEXTURE, HamsterModel.Variant.BLACK.resourceLocation.toString());
		this.entityData.define(OVERLAY_TEXTURE, HamsterMarkingLayer.Overlay.NONE.resourceLocation.toString());
	}

	public enum Gender {
		FEMALE,
		MALE
	}

	public boolean isFemale() {
		return this.getGender() == 0;
	}

	public boolean isMale() {
		return this.getGender() == 1;
	}

	public static final EntityDataAccessor<Integer> GENDER = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.INT);

	public int getGender() {
		return this.entityData.get(GENDER);
	}

	public void setGender(int gender) {
		this.entityData.set(GENDER, gender);
	}

	public boolean canParent() {
		return !this.isBaby() && this.isInLove();
	}

	public boolean canMate(Animal animal) {
		if (animal == this) {
			return false;
		} else if (!(animal instanceof Hamster)) {
			return false;
		} else {
			if (!LivestockOverhaulCommonConfig.GENDERS_AFFECT_BREEDING.get()) {
				return this.canParent() && ((Hamster) animal).canParent();
			} else {
				Hamster partner = (Hamster) animal;
				if (this.canParent() && partner.canParent() && this.getGender() != partner.getGender()) {
					return true;
				}

				boolean partnerIsFemale = partner.isFemale();
				boolean partnerIsMale = partner.isMale();
				if (LivestockOverhaulCommonConfig.GENDERS_AFFECT_BREEDING.get() && this.canParent() && partner.canParent()
						&& ((isFemale() && partnerIsMale) || (isMale() && partnerIsFemale))) {
					return isFemale();
				}
			}
		}
		return false;
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		Hamster hamster = (Hamster) ageableMob;
		if (ageableMob instanceof Hamster) {
			Hamster hamster1 = (Hamster) ageableMob;
			hamster = EntityTypes.HAMSTER_ENTITY.get().create(serverLevel);

			int i = this.random.nextInt(9);
			int variant;
			if (i < 4) {
				variant = this.getVariant();
			} else if (i < 8) {
				variant = hamster1.getVariant();
			} else {
				variant = this.random.nextInt(HamsterModel.Variant.values().length);
			}

			int j = this.random.nextInt(5);
			int overlay;
			if (j < 2) {
				overlay = this.getOverlayVariant();
			} else if (j < 4) {
				overlay = hamster1.getOverlayVariant();
			} else {
				overlay = this.random.nextInt(HamsterMarkingLayer.Overlay.values().length);
			}

			int k = this.random.nextInt(5);
			int breed;
			if (k < 2) {
				breed = this.getOverlayVariant();
			} else if (k < 4) {
				breed = hamster1.getOverlayVariant();
			} else {
				breed = this.random.nextInt(Breed.values().length);
			}

			int gender;
			gender = this.random.nextInt(Gender.values().length);

			hamster.setVariant(variant);
			hamster.setOverlayVariant(overlay);
			hamster.setGender(gender);
			hamster.setBreed(breed);
		}

		return hamster;
	}

	public enum Breed {
		SYRIAN(new ResourceLocation(HamtasticHamsters.MODID, "geo/hamster.geo.json")),
		FLUFFY(new ResourceLocation(HamtasticHamsters.MODID, "geo/hamster_fluffy.geo.json")),
		DWARF(new ResourceLocation(HamtasticHamsters.MODID, "geo/hamster_dwarf.geo.json"));

		public final ResourceLocation resourceLocation;

		Breed(ResourceLocation resourceLocation) {
			this.resourceLocation = resourceLocation;
		}

		public static Breed breedFromOrdinal(int ordinal) {
			return Breed.values()[ordinal % Breed.values().length];
		}
	}

	public SimpleContainer inventory;

	public LazyOptional<?> itemHandler = null;

	public void updateInventory() {
		SimpleContainer tempInventory = this.inventory;
		this.inventory = new SimpleContainer(this.getInventorySize());

		if(tempInventory != null) {
			tempInventory.removeListener(this);
			int maxSize = Math.min(tempInventory.getContainerSize(), this.inventory.getContainerSize());

			for(int i = 0; i < maxSize; i++) {
				ItemStack itemStack = tempInventory.getItem(i);
				if(!itemStack.isEmpty()) {
					this.inventory.setItem(i, itemStack.copy());
				}
			}
		}
		this.inventory.addListener(this);
		this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.inventory));
	}

	@Override
	public void dropEquipment() {
		if(!this.level().isClientSide) {
			super.dropEquipment();
			Containers.dropContents(this.level(), this, this.inventory);
		}
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		if(this.itemHandler != null) {
			LazyOptional<?> oldHandler = this.itemHandler;
			this.itemHandler = null;
			oldHandler.invalidate();
		}
	}

	public int getInventorySize() {
		return 3;
	}

	public SimpleContainer getInventory() {
		return this.inventory;
	}

	public void openInventory(Player player) {
		if(player instanceof ServerPlayer serverPlayer && this.isTame()) {
			NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider((containerId, inventory, p) -> {
				return new HamsterMenu(containerId, inventory, this.inventory, this);
			}, this.getDisplayName()), (data) -> {
				data.writeInt(this.getInventorySize());
				data.writeInt(this.getId());
			});
		}
	}

	@Override
	public void containerChanged(Container p_18983_) {
		return;
	}

	public boolean hasFullInventory() {
		for (int i = 0; i < inventory.getContainerSize(); i++) {
			if (inventory.getItem(i).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	static final Predicate<ItemEntity> HAMSTER_SEEKS = (itemEntity) -> {
		return !itemEntity.hasPickUpDelay() && itemEntity.isAlive() && itemEntity.getItem().is(HHTags.Items.HAMSTER_SEEKS);
	};

	public class HamsterSearchForItemsGoal extends Goal {

		public HamsterSearchForItemsGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			if (isOrderedToSit()) {
				return false;
			} else if (hasFullInventory()) {
				return false;
			} else if (Hamster.this.getTarget() == null && Hamster.this.getLastHurtByMob() == null) {
				if (Hamster.this.getRandom().nextInt(reducedTickDelay(5)) != 0) {
					return false;
				} else {
					List<ItemEntity> list = Hamster.this.level().getEntitiesOfClass(ItemEntity.class, Hamster.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Hamster.HAMSTER_SEEKS);
					return !list.isEmpty();
				}
			} else {
				return false;
			}
		}

		@Override
		public void tick() {
			List<ItemEntity> itemEntities = level().getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Hamster.HAMSTER_SEEKS);

			if (!itemEntities.isEmpty() && !hasFullInventory()) {
				ItemEntity itemEntity = itemEntities.get(0);
				getNavigation().moveTo(itemEntity, 1.2D);

				if (distanceToSqr(itemEntity) < 4.0D && itemEntity.getItem().is(HHTags.Items.HAMSTER_SEEKS)) {
					pickUpItem(itemEntity);
				}
			}
		}

		@Override
		public void start() {
			List<ItemEntity> itemEntities = level().getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Hamster.HAMSTER_SEEKS);
			if (!itemEntities.isEmpty()) {
				getNavigation().moveTo(itemEntities.get(0), 1.2D);
			}
		}

		public void pickUpItem(ItemEntity itemEntity) {
			if (!hasFullInventory() && itemEntity.getItem().is(HHTags.Items.HAMSTER_SEEKS) && this.canUse()) {
				ItemStack itemStack = itemEntity.getItem();

				for (int i = 0; i < getInventory().getContainerSize(); i++) {
					ItemStack inventoryStack = getInventory().getItem(i);

					if (!inventoryStack.isEmpty() && inventoryStack.is(itemStack.getItem()) && inventoryStack.getCount() < inventoryStack.getMaxStackSize() && itemStack.is(HHTags.Items.HAMSTER_SEEKS)) {
						int j = inventoryStack.getMaxStackSize() - inventoryStack.getCount();
						int k = Math.min(j, itemStack.getCount());
						inventoryStack.grow(k);
						itemStack.shrink(k);

						if (itemStack.isEmpty()) {
							itemEntity.discard();
							break;
						}
					}
				}

				if (!itemStack.isEmpty() && itemStack.is(HHTags.Items.HAMSTER_SEEKS) && this.canUse()) {
					for (int i = 0; i < getInventory().getContainerSize(); i++) {
						if (getInventory().getItem(i).isEmpty()) {
							getInventory().setItem(i, itemStack);
							itemEntity.discard();
							break;
						}
					}
				}
			}
		}
	}

}
