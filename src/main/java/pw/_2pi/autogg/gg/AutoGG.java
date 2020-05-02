package pw._2pi.autogg.gg;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import pw._2pi.autogg.util.AutoGGThreadFactory;
import pw._2pi.autogg.util.ConfigUtil;
import pw._2pi.autogg.util.GetTriggers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Mod(modid = "autogg", version = "2.0.5", clientSideOnly = true, acceptedMinecraftVersions = "[1.12, 1.12.2]")
public class AutoGG {
    public static final String MODID = "autogg";
    public static final String VERSION = "2.0.5";
    public static final ExecutorService THREAD_POOL;
    private static AutoGG instance;

    static {
        THREAD_POOL = Executors.newCachedThreadPool(new AutoGGThreadFactory());
    }

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
    public void init(final FMLInitializationEvent event) {
        AutoGG.instance = this;
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new GGListener());
        ClientCommandHandler.instance.registerCommand(new GGCommand());
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
        this.triggers = (List<String>) triggers;
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
}
