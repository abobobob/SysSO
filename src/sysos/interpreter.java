package sysos;

import sysos.process_manager.process;

public class interpreter {
	public Memory m = new Memory();
	public FileSystem f = new FileSystem();
	interpreter(Memory m, FileSystem f)
	{
		this.m=m;
		this.f=f;
	}
void exe()
{
	process pr=Main.P.find(Main.OBECNY_PROCES);
	String roz;
	roz=m.readUntilSpace(pr.counter);
	pr.counter+=roz.length()+1;
	switch(roz)
	{
	case "AD":
	{
		int idrej = 0;
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		if(roz.equals("R1")) idrej=1;
		if(roz.equals("R2")) idrej=2;
		if(roz.equals("R3")) idrej=3;
		if(roz.equals("R4")) idrej=4;
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		int liczba;
		if(roz.charAt(0)=='[')
		{
			String pom;
			pom=roz.substring(1, 2);
			int p=Integer.valueOf(pom);
			liczba=Integer.valueOf(m.readUntilSpace(p));
		}
		if(roz.equals("R1"))
			liczba=pr.A;
		if(roz.equals("R2"))
			liczba=pr.B;
		if(roz.equals("R3"))
			liczba=pr.C;
		if(roz.equals("R4"))
			liczba=pr.D;
		else
			liczba=Integer.valueOf(roz);
		if(idrej==1)
			pr.A+=liczba;
		if(idrej==2)
			pr.B+=liczba;
		if(idrej==3)
			pr.C+=liczba;
		if(idrej==4)
			pr.D+=liczba;
	} break;
	case "SU":
	{
		int idrej = 0;
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		if(roz.equals("R1")) idrej=1;
		if(roz.equals("R2")) idrej=2;
		if(roz.equals("R3")) idrej=3;
		if(roz.equals("R4")) idrej=4;
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		int liczba;
		if(roz.charAt(0)=='[')
		{
			String pom;
			pom=roz.substring(1, 2);
			int p=Integer.valueOf(pom);
			liczba=Integer.valueOf(m.readUntilSpace(p));
		}
		if(roz.equals("R1"))
			liczba=pr.A;
		if(roz.equals("R2"))
			liczba=pr.B;
		if(roz.equals("R3"))
			liczba=pr.C;
		if(roz.equals("R4"))
			liczba=pr.D;
		else
			liczba=Integer.valueOf(roz);
		if(idrej==1)
			pr.A-=liczba;
		if(idrej==2)
			pr.B-=liczba;
		if(idrej==3)
			pr.C-=liczba;
		if(idrej==4)
			pr.D-=liczba;
	} break;
	case "MU":
	{
		int idrej = 0;
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		if(roz.equals("R1")) idrej=1;
		if(roz.equals("R2")) idrej=2;
		if(roz.equals("R3")) idrej=3;
		if(roz.equals("R4")) idrej=4;
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		int liczba;
		if(roz.charAt(0)=='[')
		{
			String pom;
			pom=roz.substring(1, 2);
			int p=Integer.valueOf(pom);
			liczba=Integer.valueOf(m.readUntilSpace(p));
		}
		if(roz.equals("R1"))
			liczba=pr.A;
		if(roz.equals("R2"))
			liczba=pr.B;
		if(roz.equals("R3"))
			liczba=pr.C;
		if(roz.equals("R4"))
			liczba=pr.D;
		else
			liczba=Integer.valueOf(roz);
		if(idrej==1)
			pr.A*=liczba;
		if(idrej==2)
			pr.B*=liczba;
		if(idrej==3)
			pr.C*=liczba;
		if(idrej==4)
			pr.D*=liczba;
	} break;
	case "MO":
	{
		int idrej = 0;
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		if(roz.equals("R1")) idrej=1;
		if(roz.equals("R2")) idrej=2;
		if(roz.equals("R3")) idrej=3;
		if(roz.equals("R4")) idrej=4;
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		int liczba;
		if(roz.charAt(0)=='[')
		{
			String pom;
			pom=roz.substring(1, 2);
			int p=Integer.valueOf(pom);
			liczba=Integer.valueOf(m.readUntilSpace(p));
		}
		if(roz.equals("R1"))
			liczba=pr.A;
		if(roz.equals("R2"))
			liczba=pr.B;
		if(roz.equals("R3"))
			liczba=pr.C;
		if(roz.equals("R4"))
			liczba =pr.D;
		else
			liczba=Integer.valueOf(roz);
		if(idrej==1)
			pr.A=liczba;
		if(idrej==2)
			pr.B=liczba;
		if(idrej==3)
			pr.C=liczba;
		if(idrej==4)
			pr.D=liczba;
	} break;
	case "JP":
	{
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		pr.counter=Integer.valueOf(roz);
	} break;
	case "JZ":
	{
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		if(roz.equals("R1"))
			if(pr.A!=0)
				pr.counter=Integer.valueOf(roz);
		if(roz.equals("R2"))
			if(pr.B!=0)
				pr.counter=Integer.valueOf(roz);
		if(roz.equals("R3"))
			if(pr.C!=0)
			pr.counter=Integer.valueOf(roz);
		if(roz.equals("R4"))
			if(pr.D!=0)
				pr.counter=Integer.valueOf(roz);
	} break;
	case "IC":
	{
		if(roz.equals("R1"))
			pr.A++;
		if(roz.equals("R2"))
			pr.B++;
		if(roz.equals("R3"))
			pr.C++;
		if(roz.equals("R4"))
			pr.D++;
	} break;
	case "DC":
	{
		if(roz.equals("R1"))
			pr.A--;
		if(roz.equals("R2"))
			pr.B--;
		if(roz.equals("R3"))
			pr.C--;
		if(roz.equals("R4"))
			pr.D--;
	} break;
	case "SR":
	{
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		if(roz.equals("R1"))
			System.out.println("Wartosc rejestru" + pr.A);
		if(roz.equals("R2"))
			System.out.println("Wartosc rejestru" + pr.B);
		if(roz.equals("R3"))
			System.out.println("Wartosc rejestru" + pr.C);
		if(roz.equals("R4"))
			System.out.println("Wartosc rejestru" + pr.D);
	} break;
	case "CF":
	{
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		f.createFile(roz);
	} break;
	case "DF":
	{
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		f.deleteFile(roz);
	} break;
	case "RF":
	{
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		System.out.println(f.readFile(roz));
	} break;
	case "WF":
	{
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		String con=m.readUntilSpace(pr.counter);
		pr.counter+=con.length()+1;
		if(con.equals("R1"))
			con=Integer.toString(pr.A);
		if(con.equals("R2"))
			con=Integer.toString(pr.B);
		if(con.equals("R3"))
			con=Integer.toString(pr.C);
		if(con.equals("R4"))
			con=Integer.toString(pr.D);
		f.writeFile(roz, con);
	} break;
	case "CP":
	{
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		process pro = new process(roz);
		String rez=m.readUntilSpace(pr.counter);
		pr.counter+=rez.length()+1;
		pro.exec("","",Integer.valueOf(rez));
		
	} break;
	case "DP":
	{
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		process_manager man= new process_manager(); 
		pr.kill(man.find_name(roz));
	} break;
	case "WC":
	{
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		if(roz.equals("R1"))
			
		if(roz.equals("R2"))
			
		if(roz.equals("R3"))
			
		if(roz.equals("R4"))
			
		
	} break;
	case "CC":
	{
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		String roz2=m.readUntilSpace(pr.counter);
		pr.counter+=roz2.length()+1;
		if(pr.next.equals(null))
			pr.fork();
		potoki.pipe(pr);
	} break;
	case "SC":
	{
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		potoki.write(pr);
	} break;
	case "RC":
	{
		roz="";
		roz=m.readUntilSpace(pr.counter);
		pr.counter+=roz.length()+1;
		potoki.read(pr.next);
	} break;
	case "EX":
	{
		pr.kill(pr.PID);
	}
	}
}
}
