package net.fabricmc.alkimicraft.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.NoWaterTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Random;

public class StarryBodyEntity extends PathAwareEntity implements Flutterer {
    private int timeline = 0;

    public StarryBodyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals(){
        this.goalSelector.add(8, new StarryBodyEntity.StarryWanderAroundGoal());
//        this.goalSelector.add(9, new SwimGoal(this));
    }

    @Override
    public boolean isInAir() {
        return !this.onGround;
    }

    private class StarryWanderAroundGoal extends Goal {

        StarryWanderAroundGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        public boolean canStart() {
            return StarryBodyEntity.this.navigation.isIdle() && StarryBodyEntity.this.random.nextInt(10) == 0;
        }

        public boolean shouldContinue() {
            return StarryBodyEntity.this.navigation.isFollowingPath();
        }

        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                StarryBodyEntity.this.navigation.startMovingAlong(StarryBodyEntity.this.navigation.findPathTo((BlockPos)(new BlockPos(vec3d)), 1), 1.0D);
            }
        }

        public void tick() {
            super.tick();
            this.addParticle(world, getX(), getX(), getZ(), getZ(), getBodyY(0.5D), ParticleTypes.ELECTRIC_SPARK);
            setRandomVector();
        }

        private void addParticle(World world, double lastX, double x, double lastZ, double z, double y, ParticleEffect effect) {
            ((ServerWorld)world).spawnParticles(effect, x, y, z, 1, (0.5F - getRandom().nextFloat())*0.5, (0.5F - getRandom().nextFloat())*0.5, (0.5F - getRandom().nextFloat())*0.5, 0.01D);
        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d3 = StarryBodyEntity.this.getRotationVec(0.0F);
            Vec3d vec3d4 = AboveGroundTargeting.find(StarryBodyEntity.this, 8, 7, vec3d3.x, vec3d3.z, 1.5707964F, 3, 1);
            return vec3d4 != null ? vec3d4 : NoPenaltySolidTargeting.find(StarryBodyEntity.this, 8, 4, -2, vec3d3.x, vec3d3.z, 1.5707963705062866D);
        }

        public void setRandomVector(){
            int i = getDespawnCounter();
            if (i > 100) {
                setVelocity(0.0f, 0.0f, 0.0f);
            } else if (getRandom().nextInt(50) == 0 || !hasVelocity()) {
                float g = (0.5F - getRandom().nextFloat()) * 0.5f;
                float h = (0.5F - getRandom().nextFloat()) * 0.5f;
                float j = (0.5F - getRandom().nextFloat()) * 0.5f;
                setVelocity(g, h, j);
                ((ServerWorld)world).spawnParticles(ParticleTypes.END_ROD, getX(), getY(), getZ(), 3, -g, -h, -j, 0.05D);
            }
        }
    }

    public boolean hasNoGravity() {
        return true;
    }

    public boolean hasVelocity() {
        return getVelocity().x != 0.0F || getVelocity().y != 0.0F || getVelocity().z != 0.0F;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    protected void swimUpward(Tag<Fluid> fluid) {
        this.setVelocity(this.getVelocity().add(0.0D, 0.01D, 0.0D));
    }

//    protected EntityNavigation createNavigation(World world) {
//        BirdNavigation birdNavigation = new BirdNavigation(this, world) {
//            public boolean isValidPosition(BlockPos pos) {
//                return !this.world.getBlockState(pos.down()).isAir();
//            }
//
//            public void tick() {
//                super.tick();
//            }
//        };
//        birdNavigation.setCanPathThroughDoors(false);
//        birdNavigation.setCanSwim(false);
//        birdNavigation.setCanEnterOpenDoors(true);
//        return birdNavigation;
//    }

}
