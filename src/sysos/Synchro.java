/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sysos;

/**
 *
 * @author Matitam
 */
public class Synchro {
//
//    public String nazwa;
//    
//    public int PROCESS_FROM_MEMORY = 0;
//    public int PROCESS_FROM_QUEUE_OF_PROCESSESS = 0;
//    
//   // process_manager.process p = new process_manager.process("");
//    
//    //public final Lock lock;
//    
//    public Synchro(String nazwa, process_manager.process p, )
//    {
//        this.nazwa = nazwa;
//        this.p = p;
//        this.Lock = lock;
//    }
//    public boolean Test_and_Set(Boolean lock)
//    {
//        if(lock == true)
//        {
//            System.out.println("INSIDE LOOP");
//            return true;
//        }
//        System.out.println("OUTSIDE LOOP");
//        return false;
//    }
//    public boolean Compare_And_Swap(Boolean OLD_VALUE, boolean EXPECTED_VALUE, boolean NEW_VALUE)
//    {
//        boolean TEMP = OLD_VALUE;
//        if(OLD_VALUE==EXPECTED_VALUE)
//        {
//            System.out.println("INSIDE LOOP");
//            OLD_VALUE=NEW_VALUE;
//        }
//        System.out.println("OUTSIDE LOOP");
//        return TEMP;
//    }
//    
//    
//    public void TO_CRITICAL_SECTION_TAS() throws InterruptedException 
//    {
//        System.out.println(nazwa+" GOT INSIDE CRITICAL SECTION");
//        while(Test_and_Set(lock.lock))
//        {
//            Thread.sleep(500);
//            System.out.println("BREAK");
//            break;
//        }
//        lock.lock = false;
//    }
//    
//    public void TO_CRITICAL_SECTION_CAS() throws InterruptedException 
//    {
//        System.out.println(nazwa+" GOT INSIDE CRITICAL SECTION");
//        while(Compare_And_Swap(lock.lock,true,false))
//        {
//            Thread.sleep(500);
//            System.out.println("BREAK");
//            break;
//        }
//        lock.lock = false;
//    }
//    
//    public void OFF_CRITICAL_SECTION()
//    {
//        System.out.println(nazwa+" GOT OFF CRITICAL SECTION");
//        lock.lock = false;
//    }
}
