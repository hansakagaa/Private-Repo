import java.util.*;

// version 0.0.1

class CourseWork{
	
	public static void printTopic(String name, int l){
		System.out.print("  ");
		for (int i = 0; i < l+46; i++){
			System.out.print("-");
		}
		System.out.println("\n |\t\t\t"+name+"\t\t\t|");
		System.out.print("  ");
		for (int i = 0; i < l+46; i++){
			System.out.print("-");
		}
		System.out.print("\n\n");
	}

	public static void homePage(){
		printTopic("WELCOME TO GDSE MANAGEMENT SYSTEM",32);
		System.out.println("[1] Add New Student\t\t\t[2]  Add New Student With Marks");
		System.out.println("[3] Add Marks\t\t\t\t[4]  Update Student Details");
		System.out.println("[5] Update Marks\t\t\t[6]  Delete Student");
		System.out.println("[7] Print Student Details\t\t[8]  Print Student Ranks");
		System.out.println("[9] Best in Programming Fundementals\t[10] Best in Database Management System\n");
	}

	public static void optionToContinue(int E,int[] i,String option,String[][] stIdName,int[][] marks,int[] total,double[] avg,int[] rank,int[][] sortMarks){
		switch(option){
			case "1" :
				addNewStudent(E,i,stIdName);break;
			case "2" :
				addNewStudentWithMarks(E,i,stIdName,marks,total,avg,rank);break;
			case "3" :
				addMarks(stIdName,marks,total,avg,rank);break;
			case "4" :
				updateStudentDetails(stIdName);break;
			case "5" :
				updateStudentMarks(stIdName,marks,total,avg,rank);break;
			case "6" :
				deleteStudent(stIdName,marks,total,avg,rank,E,i);break;
			case "7" :
				printStudentDetail(stIdName,marks,total,avg,rank);break;
			case "8" :
				printStudentRanks(stIdName,total,avg,rank,marks);break;
			case "9" :
				bestInProgramming(sortMarks,stIdName,marks);break;
			case "10":
				bestInDatabase(sortMarks,stIdName,marks);break;
		}
	}

	public static void main(String args[]){
		Scanner input = new Scanner(System.in);
		final int E = 10 ;
		String[][] stIdName = new String[E][2];
		int[][] sortMarks = new int[2][E];
		int[][] marks = new int[E][2];
		int[] rank = new int[E];
		int[] total = new int[E];
		double[] avg = new double[E];
		String option;
		int[] i={0};
		do{
			homePage();
			do{
				System.out.print("Enter an option to continue > ");
				option = input.next();
			}while(!option.equals("0") && !option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4")
			&& !option.equals("5") && !option.equals ("6") && !option.equals("7") && !option.equals("8") && !option.equals("9") && !option.equals("10"));
			clearConsole();
			optionToContinue(E,i,option,stIdName,marks,total,avg,rank,sortMarks);
		}while(!option.equals("0"));
		System.out.println("\nThank You..!");
	}
//01	
	public static void addNewStudent(int E,int[] i,String[][] stIdName){
		Scanner input = new Scanner(System.in);
		char yOrN;
		do{
			printTopic("ADD NEW STUDENT",8);
			addStudent(E,i,stIdName);
			if(i[0]==E){return;}
			System.out.print("Student has been added successfully. ");
			i[0]++;
			do{
				System.out.print("Do you want to add a new student (Y/n) >");
				yOrN = input.next().charAt(0);
			}while(yOrN != 'Y' && yOrN != 'y' && yOrN!='y' && yOrN!='N' && yOrN!='n');
			clearConsole();
		}while(yOrN=='Y' | yOrN=='y');
		
	}
//02	
	public static void addNewStudentWithMarks(int E,int[] i,String[][] stIdName,int[][] marks,int[] total,double[] avg,int[] rank){
		Scanner input = new Scanner(System.in);
		char yOrN;
		do{ 
			printTopic("ADD NEW STUDENT WITH MARKS",24);
			addStudent(E,i,stIdName);
			if(i[0]==E){return;}
			addStudentMarks(i,marks,total,avg,rank,"");
			System.out.print("\n\nStudent has been added successfully. ");
			i[0]++;
			do{
				System.out.print("Do you want to add a new student (Y/n) >");
				yOrN = input.next().charAt(0); 
			}while(yOrN != 'Y' && yOrN != 'y' && yOrN!='y' && yOrN!='N' && yOrN!='n');
			clearConsole();
		}while(yOrN=='Y' | yOrN=='y');
			
	}
//03	
	public static void addMarks(String[][] stIdName,int[][] marks,int[] total,double[] avg,int[] rank){
		Scanner input = new Scanner(System.in);
		int[] j = new int[1];
		char yOrN;
	L1: do{
			printTopic("ADD MARKS",8);
			yOrN = searchStudent(j,stIdName);
			if(yOrN=='N' || yOrN=='n'){
				clearConsole();
				break L1;
			}
			System.out.print("Student Name : "+stIdName[j[0]][1]);
			if(marks[j[0]][0]!=0 || marks[j[0]][1]!=0){
				System.out.println("\nThis student's marks have been already added.If you want to update the marks, please use [5] Update Marks option.");
			}else{
				addStudentMarks(j,marks,total,avg,rank,"");
				System.out.print("Marks have been added. ");
			}
			do{
				System.out.print("Do you want to add marks for another student? (Y/n) >");
				yOrN = input.next().charAt(0);
			}while(yOrN != 'Y' && yOrN != 'y' && yOrN!='y' && yOrN!='N' && yOrN!='n');
			clearConsole();
		}while(yOrN=='Y' | yOrN=='y');
	}
//04	
	public static void updateStudentDetails(String[][] stIdName){
		Scanner input = new Scanner(System.in);
		int[] j = new int[1];
		char yOrN;
	L1: do{
			printTopic("UPDATE STUDENT DETAILS",16);
			yOrN = searchStudent(j,stIdName);
			if(yOrN=='N' || yOrN=='n'){
				clearConsole();
				break L1;
			}
			System.out.print("Student Name : "+stIdName[j[0]][1]);
			System.out.print("\nEnter the new student name : ");
			stIdName[j[0]][1] = input.next();
			System.out.println("Student details has been updated successfully.");
			do{
				System.out.print("Do you want to update another student datails? (Y/n) >");
				yOrN = input.next().charAt(0);
			}while(yOrN != 'Y' && yOrN != 'y' && yOrN!='y' && yOrN!='N' && yOrN!='n');
			clearConsole();
		}while(yOrN=='Y' | yOrN=='y');

	}
//05
	public static void updateStudentMarks(String[][] stIdName,int[][] marks,int[] total,double[] avg,int[] rank){
		Scanner input = new Scanner(System.in);
		int[] j = new int[1];
		char yOrN;
	L1: do{
			printTopic("UPDATE MARKS",8);
			yOrN = searchStudent(j,stIdName);
			if(yOrN=='N' || yOrN=='n'){
				clearConsole();
				break L1;
			}
			System.out.print("Student Name : "+stIdName[j[0]][1]);
			if(marks[j[0]][0]==0 | marks[j[0]][1]==0){
				System.out.print("\nThis student's marks yet to be added.");
			}else{
				System.out.println("\nProgramming Fundamentals Marks    : "+marks[j[0]][0]+"\nDatabase Management System Marks  : "+marks[j[0]][1]);
				addStudentMarks(j,marks,total,avg,rank,"Enter New ");
				System.out.println("Marks have been updated successfully.");
			}
			do{
				System.out.print("Do you want to update marks for another student? (Y/n) >");
				yOrN = input.next().charAt(0);
			}while(yOrN != 'Y' && yOrN != 'y' && yOrN!='y' && yOrN!='N' && yOrN!='n');
			clearConsole();
		}while(yOrN=='Y' | yOrN=='y');
	}
//06
	public static void deleteStudent(String[][] stIdName,int[][] marks,int[] total,double[] avg,int[] rank,int E,int[] i){
		Scanner input = new Scanner(System.in);
		int[] j = new int[1];
		char yOrN;
	L1: do{
			printTopic("DELETE STUDENT",8);
			yOrN = searchStudent(j,stIdName);
			if(yOrN=='N' || yOrN=='n'){
				clearConsole();
				break L1;
			}
			removeTheElement(stIdName,marks,total,avg,rank,E,j[0]);
			System.out.println("Student has been delete successfully.");
			i[0]--;
			do{
				System.out.print("Do you want to delete another student? (Y/n) >");
				yOrN = input.next().charAt(0);
			}while(yOrN != 'Y'&& yOrN != 'y' && yOrN!='y' && yOrN!='N' && yOrN!='n');
			creatRank(total,rank);
			clearConsole();
		}while(yOrN=='Y' | yOrN=='y');
	}
//07
	public static void printStudentDetail(String[][] stIdName,int[][] marks,int[] total,double[] avg,int[] rank){
		Scanner input = new Scanner(System.in);
		int[] j = new int[1];
		char yOrN;
	L1: do{
			printTopic("PRINT STUDENT DETAILS",16);
			yOrN = searchStudent(j,stIdName);
			if(yOrN=='N' || yOrN=='n'){
				clearConsole();
				break L1;
			}
			System.out.print("Student Name : "+stIdName[j[0]][1]);
			if(marks[j[0]][0]==0 & marks[j[0]][1]==0){
				System.out.print("\nMarks yet to be added.");
			}else{
				printDetails(j,marks,total,avg,rank);
			}
			System.out.println("\n");
			do{
				System.out.print("Do you want to search another student detail? (Y/n) >");
				yOrN = input.next().charAt(0);
			}while(yOrN != 'Y'&& yOrN != 'y' && yOrN!='y' && yOrN!='N' && yOrN!='n');
			clearConsole();
		}while(yOrN=='Y' || yOrN=='y');
	}
//08
	public static void printStudentRanks(String[][] stIdName,int[] total,double[] avg,int[] rank,int[][] marks){
		Scanner input = new Scanner(System.in);
		printTopic("PRINT STUDENT RANKS",16);
		char yOrN;
		int s = 55;
		System.out.print("\n ");
		for(int i = 0; i < s; i++){
			if(i==0 | i==7 | i==15 | i==31 | i==43| i==54){
				System.out.print("+");
			}else{
				System.out.print("-");
			}
		}
		System.out.print("\n |Rank\t|ID\t|Name\t\t|Total Marks|Avg. marks|\n ");
		for(int i = 0; i < s; i++){
			if(i==0 | i==7 | i==15 | i==31 | i==43| i==54){
				System.out.print("+");
			}else{
				System.out.print("-");
			}
		}
		creatRank(total,rank);
		printRank(stIdName,total,avg,rank);
		System.out.print("\n ");
		for(int i = 0; i < s; i++){
			if(i==0 | i==7 | i==15 | i==31 | i==43 | i==54){
				System.out.print("+");
			}else{
				System.out.print("-");
			}
		}
		System.out.println();
		do{
			System.out.print("Do you want to go back to main menu? (Y/n) >");
			yOrN = input.next().charAt(0);
		}while(yOrN != 'Y' && yOrN != 'y');
		clearConsole();
	}
//09
	public static void bestInProgramming(int[][] sortMarks,String[][] stIdName,int[][] marks){
		Scanner input = new Scanner(System.in);
		printTopic("BEST IN PROGRAMMING FUNDAMENTALS",32);
		creatPfDbMarks(marks,sortMarks);
		char yOrN;
		int s = 46;
		System.out.print("\n ");
		for(int i = 0; i < s; i++){
			if(i==0 | i==7 | i==23 | i==33 | i==45){
				System.out.print("+");
			}else{
				System.out.print("-");
			}
		}
		System.out.print("\n |ID\t|Name\t\t|PF Marks |DBMS Marks |\n ");
		for(int i = 0; i < s; i++){
			if(i==0 | i==7 | i==23 | i==33 | i==45){
				System.out.print("+");
			}else{
				System.out.print("-");
			}
		}
		printPfMarks(sortMarks,stIdName,marks);
		System.out.print("\n ");
		for(int i = 0; i < s; i++){
			if(i==0 | i==7 | i==23 | i==33 | i==45){
				System.out.print("+");
			}else{
				System.out.print("-");
			}
		}
		System.out.println();
		do{
			System.out.print("Do you want to go back to main menu? (Y/n) >");
			yOrN = input.next().charAt(0);
		}while(yOrN != 'Y' && yOrN != 'y');
		clearConsole();
	}
//10
	public static void bestInDatabase(int[][] sortMarks,String[][] stIdName,int[][] marks){
		Scanner input = new Scanner(System.in);
		printTopic("BEST IN DATABASE MANEGEMENT SYSTEM",32);
		creatPfDbMarks(marks,sortMarks);
		char yOrN;
		int s = 46;
		System.out.print("\n ");
		for(int i = 0; i < s; i++){
			if(i==0 | i==7 | i==23 | i==35 | i==45){
				System.out.print("+");
			}else{
				System.out.print("-");
			}
		}
		System.out.print("\n |ID\t|Name\t\t|DBMS Marks |PF Marks |\n ");
		for(int i = 0; i < s; i++){
			if(i==0 | i==7 | i==23 | i==35 | i==45){
				System.out.print("+");
			}else{
				System.out.print("-");
			}
		}
		printDbMarks(sortMarks,stIdName,marks);
		System.out.print("\n ");
		for(int i = 0; i < s; i++){
			if(i==0 | i==7 | i==23 | i==35 | i==45){
				System.out.print("+");
			}else{
				System.out.print("-");
			}
		}
		System.out.println();
		do{
			System.out.print("Do you want to go back to main menu? (Y/n) >");
			yOrN = input.next().charAt(0);
		}while(yOrN != 'Y' && yOrN != 'y');
		clearConsole();
	}

	public static void addStudent(int E,int[] i,String[][] stIdName){
		Scanner input = new Scanner(System.in);
		boolean option;
		String stId;
		char yOrN;
		do{
			System.out.print("Enter Student ID   : ");
			stId = input.next();
			option=false;
			for (int j = 0; j < stIdName. length; j++){
				if(stId.equals(stIdName[j][0])){
					System.out.println("The Student ID already exists\n");
					option=true;
				}
			}
		}while(option);
		if(i[0]==E){
			System.out.println("This student is not allowed to enter...");
			do{
				System.out.print("Do you want to go back to main menu? (Y/n) >");
				yOrN = input.next().charAt(0);
			}while(yOrN != 'Y' && yOrN != 'y');
			clearConsole();
		}else{
			stIdName[i[0]][0] = stId;
			System.out.print("Enter Student Name : ");
			stIdName[i[0]][1] = input.next();
		}
	}
	
	public static void removeTheElement(String[][] stIdName,int[][] marks,int[] total,double[] avg,int[] rank,int E,int I){
		String[][] tmStIdName = new String[E][2];
		int[][] tmMarks = new int[E][2];
		int[] tmTotal = new int[E];
		double[] tmAvg = new double[E];
		for(int i=0; i<2; i++){
			for(int j=0,k=0; j<E; j++){
				if (j == I) {
					continue;
				}
				tmStIdName[k][i]=stIdName[j][i];
				tmMarks[k][i]=marks[j][i];
				k++;
			}
		}
		for(int j=0,k=0; j<E; j++){
			if (j == I) {
				continue;
			}
			tmTotal[k]=total[j];
			tmAvg[k]=avg[j];
			k++;
		}
		for (int i = 0; i < E; i++){
			for (int j = 0; j < 2; j++){
				stIdName[i][j]=tmStIdName[i][j];
				marks[i][j]=tmMarks[i][j];
			}
		}
		for (int i = 0; i < E; i++){
			total[i]=tmTotal[i];
			avg[i]=tmAvg[i];
		}
		creatRank(total,rank);
	}

	public static char searchStudent(int[] i,String[][] stIdName){
		Scanner input = new Scanner(System.in);
		char yOrN;
		L1: do{
			System.out.print("Enter Student ID   : ");
			String stId = input.next();
			int num = 0;
			for (int j = 0; j < stIdName.length; j++){
				if(stId.equals(stIdName[j][0])){
					i[0]=j;
					num = 1;
				}
			}
			switch(num){
				case 1 :
					yOrN ='Y';
					break L1;
				default :
					System.out.print("Invalid Student ID. ");
					System.out.println("If you want to add the student, please use [1] Add New Student option.");
					do{
						System.out.print("Do you want to search again? (Y/n) >");
						yOrN = input.next().charAt(0);
					}while(yOrN != 'Y' && yOrN != 'y' && yOrN!='N' && yOrN!='n');
					System.out.println();
			}
		}while(yOrN=='Y' || yOrN=='y');
		return yOrN;
	}
	
	public static void addStudentMarks(int[] i,int[][] marks,int[] total,double[] avg,int[] rank,String tp){
		Scanner input = new Scanner(System.in);
		    boolean option;
			do{
				option=false;
				System.out.print("\n"+tp+"Programming Fundamentals Marks    : ");
				int mark = input.nextInt();
				if(mark<0 || mark>100){
					System.out.println("Invalid marks, please enter correct marks.\n");
					option=true;
				}else{
					marks[i[0]][0] = mark;
					total[i[0]]+=marks[i[0]][0];
				}
			}while(option);
			do{
				option=false;
				System.out.print(tp+"Database Management System Marks  : ");
				int mark = input.nextInt();
				if(mark<0 || mark>100){
					System.out.println("Invalid marks, please enter correct marks.\n");
					option=true;
				}else{
					marks[i[0]][1] = mark;
					total[i[0]]+=marks[i[0]][1];
				}
			}while(option);
			creatAvg(total,avg);
			creatRank(total,rank);
	}

	public static void creatAvg(int[] total,double[] avg){
		for (int i = 0; i < total.length; i++){
			avg[i]=(double)total[i]/2;
		}
	}
	
	public static void creatRank(int[] total,int[] rank){
		for (int i = 0; i < total.length; i++){
			rank[i]=total[i];
		}
		for (int j = 0; j < rank.length; j++){
			for (int i = rank.length-1; i > 0; i--){
				if(rank[i] > rank[i-1]){
					int temp = rank[i];
					rank[i] = rank[i-1];
					rank[i-1] = temp;
				}
			}
		}
	}
	
	public static void creatPfDbMarks(int[][] marks,int[][] sortMarks){
		for (int i = 0; i < marks.length; i++){
			sortMarks[0][i]=marks[i][0];
			sortMarks[1][i]=marks[i][1];
		}
		for (int j = 0; j < marks.length; j++){
			for (int i = marks.length-1; i > 0; i--){
				if(sortMarks[0][i] > sortMarks[0][i-1]){
					int temp = sortMarks[0][i];
					sortMarks[0][i] = sortMarks[0][i-1];
					sortMarks[0][i-1] = temp;
				}
				if(sortMarks[1][i] > sortMarks[1][i-1]){
					int temp = sortMarks[1][i];
					sortMarks[1][i] = sortMarks[1][i-1];
					sortMarks[1][i-1] = temp;
				}
			}
		}
	}
	 
	public static void printDetails(int[] j,int[][] marks,int[] total,double[] avg,int[] rank){
		int index = 0;
		for (int i = 0; i < rank.length; i++){
			if(rank[i]==total[j[0]]){
				index = i;
			}
		}
		int s = 64;
		System.out.print("\n ");
		for(int i = 0; i < s; i++){
			if(i==0 | i==39 | i==63){
				System.out.print("+");
			}else{
				System.out.print("-");
			}
		}
		System.out.println("\n |Programming Fundementals Marks\t|\t\t"+marks[j[0]][0]+"\t|");
		System.out.println(" |Database Management System Marks\t|\t\t"+marks[j[0]][1]+"\t|");
		System.out.print(" |Total Marks\t\t\t\t|\t\t"+total[j[0]]+"\t|\n |Avg. Marks\t\t\t\t|\t\t");
		System.out.printf("%.2f",avg[j[0]]);
		System.out.println("\t|");
		switch(index){
			case 0 :
				System.out.println(" |Rank\t\t\t\t\t|\t\t"+(index+1)+"(First)|");
				break;
			case 1 :
				System.out.println(" |Rank\t\t\t\t\t|\t\t"+(index+1)+"(Second)|");
				break;
			case 2 :
				System.out.println(" |Rank\t\t\t\t\t|\t\t"+(index+1)+"(Third)|");
				break;
			default :
				System.out.println(" |Rank\t\t\t\t\t|\t\t"+(index+1)+"\t|");
		}
		System.out.print(" ");
		for(int i = 0; i < s; i++){
			if(i==0 | i==39 | i==63){
				System.out.print("+");
			}else{
				System.out.print("-");
			}
		}
	}

	public static void printRank(String[][] stIdName,int[] total,double[] avg,int[] rank){
		int[] index = new int[stIdName.length];
		int index1 = stIdName.length;
		for (int i = 0; i < rank.length; i++){
			for(int j = 0; j < stIdName.length; j++){
				if(rank[i]==total[j]){
					index[i]=j;
				}
			}
		}
		for (int i = 0; i < rank.length; i++){
			if (rank[i]==0){
				index1=i;
				break; 
			}
		}
		for (int i = 0; i < index1; i++){
			System.out.print("\n |"+(i+1)+"\t|"+stIdName[index[i]][0]+"\t|"+stIdName[index[i]][1]+"\t\t|\t "+total[index[i]]+"|\t  ");
			System.out.printf("%.2f",avg[index[i]]);
			System.out.print("|");
		}
	}
	
	public static void printPfMarks(int[][] sortMarks,String[][] stIdName,int[][] marks){
		int[] index = new int[marks.length];
		int index1 = marks.length;
		for (int i = 0; i < marks.length; i++){
			for (int j = 0; j < marks.length; j++){
				if(sortMarks[0][i]==marks[j][0]){
					index[i]=j;
				}
			}
		}
		for (int i = 0; i < marks.length; i++){
			if (sortMarks[0][i]==0){
				index1=i;
				break; 
			}
		}
		for (int i = 0; i < index1; i++){
			System.out.print("\n |"+stIdName[index[i]][0]+"\t|"+stIdName[index[i]][1]+"\t\t|"+sortMarks[0][i]+"\t  |"+marks[index[i]][1]+"\t      |");
		}
	}
	
	public static void printDbMarks(int[][] sortMarks,String[][] stIdName,int[][] marks){
		int[] inde = new int[marks.length];
		int index1 = marks.length;
		for (int i = 0; i < marks.length; i++){
			for (int j = 0; j < marks.length; j++){
				if(sortMarks[1][i]==marks[j][1]){
					inde[i]=j;
				}
			}
		}
		for (int i = 0; i < marks.length; i++){
			if (sortMarks[1][i]==0){
				index1=i;
				break; 
			}
		}	
		for (int i = 0; i < index1; i++){
			System.out.print("\n |"+stIdName[inde[i]][0]+"\t|"+stIdName[inde[i]][1]+"\t\t|"+sortMarks[1][i]+"\t    |"+marks[inde[i]][0]+"\t      |");
		}
	}

	public final static void clearConsole(){ 
		try {
			final String os = System.getProperty("os.name"); 
			if (os.contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
			System.out.print("\033[H\033[2J"); 
			System.out.flush();
			}
		} catch (final Exception e) {
			e.printStackTrace(); 
		}
	}
}
