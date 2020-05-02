package pw._2pi.autogg.gg;

import net.minecraft.server.*;
import pw._2pi.autogg.util.*;
import net.minecraft.command.*;
import net.minecraft.util.text.*;

public class GGCommand extends CommandBase
{
    public String getName() {
        return "autogg";
    }
    
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public boolean canSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 0 || args.length > 2) {
            this.showSyntaxError(sender);
            return;
        }
        final String s = args[0];
        switch (s) {
            case "toggle":
            case "t": {
                AutoGG.getInstance().setToggled();
                this.showMessage(TextFormatting.GRAY + "AutoGG: " + (AutoGG.getInstance().isToggled() ? (TextFormatting.GREEN + "On") : (TextFormatting.RED + "Off")), sender);
                break;
            }
            case "delay":
            case "d":
            case "time": {
                if (args.length == 2) {
                    try {
                        final int delay = Integer.parseInt(args[1]);
                        if (delay < 0 || delay > 5) {
                            throw new NumberFormatException("Invalid integer");
                        }
                        AutoGG.getInstance().setDelay(delay);
                        ConfigUtil.setConfigDelay();
                        this.showMessage(TextFormatting.GRAY + "AutoGG delay set to " + TextFormatting.GREEN + AutoGG.getInstance().getDelay() + "s", sender);
                    }
                    catch (NumberFormatException e) {
                        this.showError("Please use an integer between 1 and 5 seconds.", sender);
                    }
                    break;
                }
                this.showMessage(TextFormatting.GRAY + "AutoGG Delay: " + TextFormatting.GREEN + AutoGG.getInstance().getDelay() + "s", sender);
                break;
            }
            default: {
                this.showSyntaxError(sender);
                break;
            }
        }
    }
    
    public String getUsage(final ICommandSender sender) {
        return "/autogg <toggle, delay [seconds]>";
    }
    
    private void showMessage(final String message, final ICommandSender sender) {
        sender.sendMessage((ITextComponent)new TextComponentString(message));
    }
    
    private void showSyntaxError(final ICommandSender sender) {
        this.showMessage(TextFormatting.RED + "Usage: " + this.getUsage(sender), sender);
    }
    
    private void showError(final String error, final ICommandSender sender) {
        this.showMessage(TextFormatting.RED + "Error: " + error, sender);
    }
}
