package sysos;

import java.util.ArrayList;
import sysos.process_manager.process;
import static sysos.process_manager.status.ACTIVE;
import static sysos.process_manager.status.READY;

public class schedulerr {
	public int readyp;
	private ArrayList<Boolean> whichqs = new ArrayList<Boolean>();
	public schedulerr() {
		for(int i=0;i<128;i++){
			whichqs.add(false);
		}
		readyp=0;
		for(int i=0;i<128;i++){
			ArrayList<process> p=new ArrayList<process>();
			qs.add(p);
		}
	}
	private ArrayList<ArrayList<process>> qs = new ArrayList<ArrayList<process>>(128);

	public process runningProcess = Main.T.new process("");
	
	public void add_to_q(process x) {
		if(x.usrpri==null) {
			x.usrpri=x.pri;
		}
                
		qs.get(x.usrpri).add(x);
		whichqs.set(x.usrpri, true);
	}
	
	private void divide_cpu() {
		for(int i=0; i<128;i++) {
			for(int j=0;j<qs.get(i).size();j++) {
				if(qs.get(i).get(j).cpu>0)
                                qs.get(i).get(j).cpu /= 2;
			}
		}
	}
	
	private void change_q(process x) {
		x.usrpri=x.pri+(x.cpu/2);
		qs.get(x.usrpri).add(x);
		whichqs.set(x.usrpri, true);
	}
	
	private void runProcess(process_manager p) {
		ArrayList<process> ready = p.ready_processes();				//dodaje do kolejki gotowe procesy
		for(;readyp < ready.size()-1;readyp++) {
			add_to_q(ready.get(readyp));
		}
		
		int i=0;
		while(whichqs.get(i)==false) {							//szuka kolejki o najnizszym priorytecie
			if(whichqs.get(i)==true) break;
			i++;
			if(i==127) return;
		}
																//ustawia pierwszy proces o najnizszym priorytecie jako running
		process x=qs.get(i).get(0);
		runningProcess=x;
		runningProcess.change_process_state(ACTIVE);
		runningProcess.Lock=false;
		qs.get(i).remove(0);		
	}

	public void check(process actual, process_manager p) {
		actual.change_process_state(READY);		
		actual.Lock=true;
		if(runningProcess.PID == null) {						//jesli zaden proces nie jest running wchodzi do runProcess gdzie wlacza sie proces z najnizszym priorytetem
			runProcess(p);										//aktualny jako running
			actual=runningProcess;
			return;
		}
		if(runningProcess.PID != null) {						//running jako aktualny, 
			runningProcess=actual;								//zmienia wartoœci cpu procesu running i odk³ada go do kolejki procesów
			divide_cpu();
			change_q(runningProcess);
			runProcess(p);
			return;
		}
		else {
			actual=runningProcess;
			return;
		}
	}
}
