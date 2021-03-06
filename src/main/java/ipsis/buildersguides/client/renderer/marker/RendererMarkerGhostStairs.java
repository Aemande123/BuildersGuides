package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumFacing;

public class RendererMarkerGhostStairs extends RendererMarker {
    @Override
    public void doRenderMarkerType(TESRMarker tesrMarker, TileEntityMarker te, double relX, double relY, double relZ, float partialTicks) {

        if (te.getBlockList() == null || te.getBlockList().isEmpty())
            return;

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {

            float rotAngle, rotX, rotY, rotZ;
            if (te.getFacing() == EnumFacing.NORTH)
                rotAngle = 180.0F;
            else if (te.getFacing() == EnumFacing.EAST)
                rotAngle = 90.0F;
            else if (te.getFacing() == EnumFacing.WEST)
                rotAngle = -90.0F;
            else
                rotAngle = 0.0F;

            renderBlockListAsStairs(te.getBlockList(), te, relX, relY, relZ, rotAngle);
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
