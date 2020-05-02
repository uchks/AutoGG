package pw._2pi.autogg.gg;

import net.minecraftforge.fml.common.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.*;
import net.minecraft.command.*;
import java.io.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import pw._2pi.autogg.util.*;
import java.util.concurrent.*;

@Mod(modid = "autogg", version = "2.0.4", clientSideOnly = true, acceptedMinecraftVersions = "[1.12, 1.12.1]")
public class AutoGG
{
    public static final String MODID = "autogg";
    public static final String VERSION = "2.0.4";
    public static final ExecutorService THREAD_POOL;
    private static AutoGG instance;
    private final Minecraft mc;
    private boolean onHypixel;
    private boolean toggle;
    private int delay;
    private List<String> triggers;
    private Boolean running;
    
    public AutoGG() {
        this.mc = Minecraft.getMinecraft();
        this.onHypixel = false;
        this.toggle = true;
        this.delay = 1;
        this.running = false;
    }
    
    public static AutoGG getInstance() {
        return AutoGG.instance;
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) throws IOException {
        AutoGG.instance = this;
        MinecraftForge.EVENT_BUS.register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)new GGListener());
        ClientCommandHandler.instance.registerCommand((ICommand)new GGCommand());
        AutoGG.THREAD_POOL.submit(new GetTriggers());
        this.delay = ConfigUtil.getConfigDelay();
    }
    
    @SubscribeEvent
    public void playerLoggedIn(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.onHypixel = (!this.mc.isSingleplayer() && event.getManager().getRemoteAddress().toString().toLowerCase().contains("hypixel.net"));
    }
    
    @SubscribeEvent
    public void playerLoggedOut(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.onHypixel = false;
    }
    
    public boolean isHypixel() {
        return this.onHypixel;
    }
    
    public List getTriggers() {
        return this.triggers;
    }
    
    public void setTriggers(final ArrayList triggers) {
        this.triggers = (List<String>)triggers;
    }
    
    public boolean isToggled() {
        return this.toggle;
    }
    
    public void setToggled() {
        this.toggle = !this.toggle;
    }
    
    public int getDelay() {
        return this.delay;
    }
    
    public void setDelay(final int delay) {
        this.delay = delay;
    }
    
    public Minecraft getMinecraft() {
        return this.mc;
    }
    
    public boolean isRunning() {
        return this.running;
    }
    
    public void setRunning(final boolean running) {
        this.running = running;
    }
    
    static {
        THREAD_POOL = Executors.newCachedThreadPool(new AutoGGThreadFactory());
    }
}
