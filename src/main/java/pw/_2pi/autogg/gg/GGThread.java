package pw._2pi.autogg.gg;

public class GGThread implements Runnable
{
    @Override
    public void run() {
        try {
            Thread.sleep(AutoGG.getInstance().getDelay() * 1000);
            AutoGG.getInstance().getMinecraft().field_71439_g.func_71165_d("/achat gg");
            Thread.sleep(2000L);
            AutoGG.getInstance().setRunning(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
