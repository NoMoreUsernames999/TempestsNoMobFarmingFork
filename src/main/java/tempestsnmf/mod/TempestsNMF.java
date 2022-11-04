package tempestsnmf.mod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tempestsnmf.mod.util.Reference;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class TempestsNMF {

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(TempestsNMF.class);
    }

    //Copied code across Endaril's mods I assume. I don't see a use for it here.
    /*@SubscribeEvent
    public static void saveConfig(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MODID)) ConfigManager.sync(MODID, Config.Type.INSTANCE);
    }*/

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public static void drops(LivingDropsEvent event) {

        //If a player killed it, drop items. Additional list of entities that cause drops (tameable mobs).
        DamageSource source = event.getSource();
        if (source.getImmediateSource() instanceof EntityPlayerMP) {
            return;
        }
        if (source.getImmediateSource() instanceof EntityWolf) {
            return;
        }
        Entity entity = source.getTrueSource();
        if (entity != null && entity.getClass() == EntityPlayerMP.class) return;
        entity = source.getImmediateSource();
        if (entity != null && entity.getClass() == EntityPlayerMP.class) return;

        //Otherwise, hard-cancel drops
        event.getDrops().clear();
        event.setCanceled(true);
    }
}