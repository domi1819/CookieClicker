package ch.jmnetwork.cookieclicker;

import ch.jmnetwork.cookieclicker.helper.Helper;
import ch.jmnetwork.cookieclicker.ui.CCUserInterface;
import ch.jmnetwork.cookieclicker.util.SaveLoadHandler;

public class CookieClickerMain
{
    public static CookieClickerMain INSTANCE;
    private final static int TICKS_PER_SECOND = 25;
    private static long lastTime = 0;
    private static long thisTime = 0;
    private static CCUserInterface ccui;
    private static CookieManager cookiemanager = new CookieManager();
    private static SaveLoadHandler slhandler = new SaveLoadHandler(cookiemanager);
    
    public static void main(String[] args)
    {
        INSTANCE = new CookieClickerMain();
        
        ccui = new CCUserInterface(cookiemanager, slhandler);
        
        while (true)
        {
            if (lastTime == 0) lastTime = System.nanoTime();
            
            thisTime = System.nanoTime();
            
            // only run this TICKS_PER_SECOND times per second
            if ((thisTime - lastTime) / 1000000 >= 1000 / TICKS_PER_SECOND)
            {
                // ======================================//
                // STUFF TO RUN EACH TICK
                // ======================================//
                
                INSTANCE.handleTick();
                ccui.updateUI();
                lastTime = System.nanoTime();
            }
        }
    }
    
    private void handleTick()
    {
        cookiemanager.decimalValue += Helper.getCookieRate() / TICKS_PER_SECOND;
        
        if (cookiemanager.decimalValue >= 1.0F)
        {
            float rem = cookiemanager.decimalValue - (cookiemanager.decimalValue % 1);
            
            cookiemanager.addCookies((int) rem);
            cookiemanager.decimalValue -= rem;
        }
    }
    
    public CookieClickerMain()
    {
        Helper.registerHelpers();
    }
    
    public int nanoToMilliseconds(long nanoTime)
    {
        return (int) (nanoTime / 1000000);
    }
}
