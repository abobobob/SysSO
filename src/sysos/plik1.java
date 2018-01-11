package sysos;
//xd
import sysos.process_manager.process;

public class plik1 {
    static potoki[] tab=new potoki[16];
    static int rozmiar=16;
    
       static public int finddes()
       {
           int number=-1;
           for(int i=0;i<16;i++)
           {
               if(tab[i].open==0)
               {
                   tab[i].open=1;
                   number=i;
               }
           }        
           return number;
       }
       
      static void closedes(process p)
       {
           int numb=p.des;
           if(tab[numb].readbytes==tab[numb].writebytes)
           {
           tab[numb].open=0;
           for(int i=tab[numb].qfreespace;i>=0;i--)
               tab[numb].myQueue.poll();
           }
       }     
    
}
