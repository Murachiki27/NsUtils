package ns.nsutils.client.model;

import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ns.nsutils.client.renderer.IItemRenderer;

@SideOnly(Side.CLIENT)
public class NsModelItemBase implements IPerspectiveAwareModel
{
	
	public static TransformType currentTransType = TransformType.NONE;
	public static EntityLivingBase currentOwner;
	public static ItemStack currentStack = ItemStack.EMPTY;
	private final IItemRenderer renderTarget;
	
	public NsModelItemBase(IItemRenderer renderer)
	{
		this.renderTarget = renderer;
	}
	
	private List<BakedQuad> emptyList = null;
	private List<BakedQuad> getEmptyList()
	{
		if(this.emptyList == null)
			this.emptyList = Lists.newArrayList(new BakedQuad(new int[28], 0, EnumFacing.UP, getParticleTexture(), false, net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM));
		return this.emptyList;
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand)
	{
		return this.getEmptyList();
	}
	
	@Override
	public boolean isAmbientOcclusion()
	{
		return this.renderTarget.isAmbientOcclusion();
	}
	
	@Override
	public boolean isGui3d()
	{
		return this.renderTarget.isGui3d();
	}
	
	@Override
	public boolean isBuiltInRenderer()
	{
		return true;
	}
	
	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(currentStack.getItem());
	}
	
	@Override
	public ItemCameraTransforms getItemCameraTransforms()
	{
		return new ItemCameraTransforms(this.renderTarget.getCameraTransforms())
		{
			@Override
			public ItemTransformVec3f getTransform(TransformType type)
			{
				currentTransType = type;
				return super.getTransform(type);
			}
		};
	}
	
	@Override
	public ItemOverrideList getOverrides()
	{
		return new ItemOverrideList(this.renderTarget.getOverride())
		{
			@Override
			public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity)
			{
				if (stack != null)
				{
					currentStack = stack;
					currentOwner = entity == null ? currentOwner : entity;
				}
				else
				{
					currentStack = ItemStack.EMPTY;
					currentOwner = null;
				}
				return super.handleItemState(originalModel, stack, world, entity);
			}
		};
	}
	
	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType)
	{
		currentTransType = cameraTransformType;
		/*
		if (this.target instanceof IPerspectiveAwareModel)
		{
			Matrix4f matrix4f = ((IPerspectiveAwareModel)this.target).handlePerspective(cameraTransformType).getRight();
			return Pair.of(this, matrix4f);
		}
		else
		{
			ItemCameraTransforms itemCameraTransforms = this.target.getItemCameraTransforms();
			ItemTransformVec3f itemTransformVec3f = itemCameraTransforms.getTransform(cameraTransformType);
			TRSRTransformation tr = new TRSRTransformation(itemTransformVec3f);
			Matrix4f mat = null;
			if (tr != null)
			{
				mat = tr.getMatrix();
			}
			return Pair.of(this, mat);
		}
		*/
		ItemCameraTransforms itemCameraTransforms = this.renderTarget.getCameraTransforms();
		TRSRTransformation transformation = new TRSRTransformation(itemCameraTransforms.getTransform(cameraTransformType));
		Matrix4f matrix = null;
		if (transformation != null)
		{
			matrix = transformation.getMatrix();
		}
		return Pair.of(this, matrix);
		//return IPerspectiveAwareModel.MapWrapper.handlePerspective(this, ModelRotation.X0_Y0, cameraTransformType);
	}
}
