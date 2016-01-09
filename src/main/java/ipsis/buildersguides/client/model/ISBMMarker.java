package ipsis.buildersguides.client.model;

import com.google.common.primitives.Ints;
import ipsis.buildersguides.block.BlockMarker;
import ipsis.buildersguides.reference.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.ISmartBlockModel;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.common.property.IExtendedBlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ISBMMarker implements ISmartBlockModel {

    public static final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Reference.MOD_ID + ":marker");

    @Override
    public IBakedModel handleBlockState(IBlockState state) {

        IExtendedBlockState extendedBlockState = (IExtendedBlockState)state;
        boolean north = extendedBlockState.getValue(BlockMarker.NORTH);
        boolean south = extendedBlockState.getValue(BlockMarker.SOUTH);
        boolean east = extendedBlockState.getValue(BlockMarker.EAST);
        boolean west = extendedBlockState.getValue(BlockMarker.WEST);
        boolean up = extendedBlockState.getValue(BlockMarker.UP);
        boolean down = extendedBlockState.getValue(BlockMarker.DOWN);
        return new BakedModelMarker(north, south, east, west, up , down);
    }

    @Override
    public List<BakedQuad> getFaceQuads(EnumFacing enumFacing) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<BakedQuad> getGeneralQuads() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return null;
    }

    public class BakedModelMarker implements IBakedModel {

        private TextureAtlasSprite sprite;

        private final boolean north;
        private final boolean south;
        private final boolean east;
        private final boolean west;
        private final boolean up;
        private final boolean down;

        public BakedModelMarker(boolean north, boolean south, boolean east, boolean west, boolean up, boolean down) {
            sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(Reference.MOD_ID_LOWER + ":blocks/" + BlockMarker.BASENAME);
            this.north = north;
            this.south = south;
            this.east = east;
            this.west = west;
            this.up = up;
            this.down = down;
        }

        @Override
        public List<BakedQuad> getFaceQuads(EnumFacing p_177551_1_) {
            return Collections.emptyList();
        }

        private int[] vertexToInts(double x, double y, double z, float u, float v) {
            return new int[] {
                    Float.floatToRawIntBits((float) x),
                    Float.floatToRawIntBits((float) y),
                    Float.floatToRawIntBits((float) z),
                    -1,
                    Float.floatToRawIntBits(sprite.getInterpolatedU(u)),
                    Float.floatToRawIntBits(sprite.getInterpolatedV(v)),
                    0
            };
        }

        private BakedQuad createQuad(Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4) {
            Vec3 normal = v1.subtract(v2).crossProduct(v3.subtract(v2));
            EnumFacing side = LightUtil.toSide((float) normal.xCoord, (float) normal.yCoord, (float) normal.zCoord);

            return new BakedQuad(Ints.concat(
                    vertexToInts(v1.xCoord, v1.yCoord, v1.zCoord, 0, 0),
                    vertexToInts(v2.xCoord, v2.yCoord, v2.zCoord, 0, 16),
                    vertexToInts(v3.xCoord, v3.yCoord, v3.zCoord, 16, 16),
                    vertexToInts(v4.xCoord, v4.yCoord, v4.zCoord, 16, 0)
            ), -1, side);
        }

        @Override
        public List<BakedQuad> getGeneralQuads() {
            List<BakedQuad> quads = new ArrayList<>();
            double o = .4;

            /**
             * If the face is active then extend the blocks in that direction
             */

            if (up) {
                quads.add(createQuad(new Vec3(1-o, 1-o, o), new Vec3(1-o, 1, o), new Vec3(1-o, 1, 1-o), new Vec3(1-o, 1-o, 1-o)));
                quads.add(createQuad(new Vec3(o, 1-o, 1-o), new Vec3(o, 1, 1-o), new Vec3(o, 1, o), new Vec3(o, 1-o, o)));
                quads.add(createQuad(new Vec3(o, 1, o), new Vec3(1-o, 1, o), new Vec3(1-o, 1-o, o), new Vec3(o, 1-o, o)));
                quads.add(createQuad(new Vec3(o, 1-o, 1-o), new Vec3(1-o, 1-o, 1-o), new Vec3(1-o, 1, 1-o), new Vec3(o, 1, 1-o)));
                quads.add(createQuad(new Vec3(o, 1, 1-o), new Vec3(1-o, 1, 1-o), new Vec3(1-o, 1, o), new Vec3(o, 1, o)));
            } else {
                quads.add(createQuad(new Vec3(o, 1-o, 1-o), new Vec3(1-o, 1-o, 1-o), new Vec3(1-o, 1-o, o), new Vec3(o, 1-o, o)));
            }

            if (down) {
                quads.add(createQuad(new Vec3(1-o, 0, o), new Vec3(1-o, o, o), new Vec3(1-o, o, 1-o), new Vec3(1-o, 0, 1-o)));
                quads.add(createQuad(new Vec3(o, 0, 1-o), new Vec3(o, o, 1-o), new Vec3(o, o, o), new Vec3(o, 0, o)));
                quads.add(createQuad(new Vec3(o, o, o), new Vec3(1-o, o, o), new Vec3(1-o, 0, o), new Vec3(o, 0, o)));
                quads.add(createQuad(new Vec3(o, 0, 1-o), new Vec3(1-o, 0, 1-o), new Vec3(1-o, o, 1-o), new Vec3(o, o, 1-o)));
                quads.add(createQuad(new Vec3(o, 0, o), new Vec3(1-o, 0, o), new Vec3(1-o, 0, 1-o), new Vec3(o, 0, 1-o)));
            } else {
                quads.add(createQuad(new Vec3(o, o, o), new Vec3(1-o, o, o), new Vec3(1-o, o, 1-o), new Vec3(o, o, 1-o)));
            }

            if (east) {
                quads.add(createQuad(new Vec3(1-o, 1-o, 1-o), new Vec3(1, 1-o, 1-o), new Vec3(1, 1-o, o), new Vec3(1-o, 1-o, o)));
                quads.add(createQuad(new Vec3(1-o, o, o), new Vec3(1, o, o), new Vec3(1, o, 1-o), new Vec3(1-o, o, 1-o)));
                quads.add(createQuad(new Vec3(1-o, 1-o, o), new Vec3(1, 1-o, o), new Vec3(1, o, o), new Vec3(1-o, o, o)));
                quads.add(createQuad(new Vec3(1-o, o, 1-o), new Vec3(1, o, 1-o), new Vec3(1, 1-o, 1-o), new Vec3(1-o, 1-o, 1-o)));
                quads.add(createQuad(new Vec3(1, o, o), new Vec3(1, 1-o, o), new Vec3(1, 1-o, 1-o), new Vec3(1, o, 1-o)));
            } else {
                quads.add(createQuad(new Vec3(1-o, o, o), new Vec3(1-o, 1-o, o), new Vec3(1-o, 1-o, 1-o), new Vec3(1-o, o, 1-o)));
            }

            if (west) {
                quads.add(createQuad(new Vec3(0, 1-o, 1-o), new Vec3(o, 1-o, 1-o), new Vec3(o, 1-o, o), new Vec3(0, 1-o, o)));
                quads.add(createQuad(new Vec3(0, o, o), new Vec3(o, o, o), new Vec3(o, o, 1-o), new Vec3(0, o, 1-o)));
                quads.add(createQuad(new Vec3(0, 1-o, o), new Vec3(o, 1-o, o), new Vec3(o, o, o), new Vec3(0, o, o)));
                quads.add(createQuad(new Vec3(0, o, 1-o), new Vec3(o, o, 1-o), new Vec3(o, 1-o, 1-o), new Vec3(0, 1-o, 1-o)));
                quads.add(createQuad(new Vec3(0, o, 1-o), new Vec3(0, 1-o, 1-o), new Vec3(0, 1-o, o), new Vec3(0, o, o)));
            } else {
                quads.add(createQuad(new Vec3(o, o, 1-o), new Vec3(o, 1-o, 1-o), new Vec3(o, 1-o, o), new Vec3(o, o, o)));
            }

            if (north) {
                quads.add(createQuad(new Vec3(o, 1-o, o), new Vec3(1-o, 1-o, o), new Vec3(1-o, 1-o, 0), new Vec3(o, 1-o, 0)));
                quads.add(createQuad(new Vec3(o, o, 0), new Vec3(1-o, o, 0), new Vec3(1-o, o, o), new Vec3(o, o, o)));
                quads.add(createQuad(new Vec3(1-o, o, 0), new Vec3(1-o, 1-o, 0), new Vec3(1-o, 1-o, o), new Vec3(1-o, o, o)));
                quads.add(createQuad(new Vec3(o, o, o), new Vec3(o, 1-o, o), new Vec3(o, 1-o, 0), new Vec3(o, o, 0)));
                quads.add(createQuad(new Vec3(o, 1-o, 0), new Vec3(1-o, 1-o, 0), new Vec3(1-o, o, 0), new Vec3(o, o, 0)));
            } else {
                quads.add(createQuad(new Vec3(o, 1-o, o), new Vec3(1-o, 1-o, o), new Vec3(1-o, o, o), new Vec3(o, o, o)));
            }
            if (south) {
                quads.add(createQuad(new Vec3(o, 1-o, 1), new Vec3(1-o, 1-o, 1), new Vec3(1-o, 1-o, 1-o), new Vec3(o, 1-o, 1-o)));
                quads.add(createQuad(new Vec3(o, o, 1-o), new Vec3(1-o, o, 1-o), new Vec3(1-o, o, 1), new Vec3(o, o, 1)));
                quads.add(createQuad(new Vec3(1-o, o, 1-o), new Vec3(1-o, 1-o, 1-o), new Vec3(1-o, 1-o, 1), new Vec3(1-o, o, 1)));
                quads.add(createQuad(new Vec3(o, o, 1), new Vec3(o, 1-o, 1), new Vec3(o, 1-o, 1-o), new Vec3(o, o, 1-o)));
                quads.add(createQuad(new Vec3(o, o, 1), new Vec3(1-o, o, 1), new Vec3(1-o, 1-o, 1), new Vec3(o, 1-o, 1)));
            } else {
                quads.add(createQuad(new Vec3(o, o, 1-o), new Vec3(1-o, o, 1-o), new Vec3(1-o, 1-o, 1-o), new Vec3(o, 1-o, 1-o)));
            }

            return quads;
        }

        @Override
        public boolean isAmbientOcclusion() {
            return true;
        }

        @Override
        public boolean isGui3d() {
            return true;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            return sprite;
        }

        @Override
        public ItemCameraTransforms getItemCameraTransforms() {
            return ItemCameraTransforms.DEFAULT;
        }
    }
}