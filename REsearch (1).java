import java.util.*;
import java.io.*;
public class REsearch{

	static String fileName;
	public static void main(String[] args){
	
		fileName= args[0];
		//File f = new File(fileName);
		//System.out.println(f.exists());	
		REsearch res=new REsearch();
		res.start();
		return;
	}

	//declaring variables
	String currentLine="";
	Item currentItem;
	int mark=0;//where we are in the text
	int point=0;


	String replaceWith="0 µ 1 1";
	String template="0 µ ";
	

	//flags
	int checkpoint=0;//this the branch state that is after the last legal character/ was null
	int fail=0;//this is 0, if its possible to match, 1 if there is no possible match
	int fullMatch;//if the current item is at this position, wouve found a substring matching the regex.	
	
	public void start(){
	
		try{
			//reads the text
			BufferedReader textReader= new BufferedReader(new FileReader(new File(fileName)));
			//reads in fsm			
			BufferedReader fsmReader=new BufferedReader(new InputStreamReader(System.in));
			
			String fsm;
			while((fsm=fsmReader.readLine())!=null&&fsm.length()!=0){

				if(fsm.charAt(0)=='0'&&fsm.charAt(fsm.length()-1)=='0'){				
					fsm=replaceWith;		
				}
				if(fsm.charAt(0)=='0'&&fsm.charAt(fsm.length()-1)!='0'){					
					
					template+=fsm.charAt(fsm.length()-1)+" "+fsm.charAt(fsm.length()-1);
					fsm=template;
				}
				//equals(lineToReplace)){fsm=replaceWith;}			
				//System.out.println(fsm);
				String[] fsmArray=fsm.split(" ");
				Item i= new Item(Integer.parseInt(fsmArray[0]),fsmArray[1],Integer.parseInt(fsmArray[2]),Integer.parseInt(fsmArray[3]));	
				Items.add(i);
									
				if(i.stateNum!=0 && i.pointer1==0 && i.pointer2==0){
					fsm=null;
					break;
				}	
			}
			Item toAdd;

			//start at the start-state
			currentItem=Items.get(0);
			currentLine=textReader.readLine();
			//know what the exit point is
			fullMatch=Items.size()-1;			

			//start by putting the start state on the stack
			Current.add(currentItem);
			
			while(currentLine!=null){
				//System.out.println("b");
				
				//if there are items in the deque
				if(Current.size()>0){
					//System.out.println("c");
					
					currentItem=pop(Current);
					toAdd=currentItem;
					toAdd.flag=1;
					Items.set(toAdd.stateNum,toAdd);

					if(currentItem.stateNum==fullMatch){					
						
						Possible.subList(0, Possible.size()).clear();
						Current.subList(0, Current.size()).clear();
						System.out.println(currentLine);
						currentLine=textReader.readLine();
						if(currentLine==null){return;}
						mark=0;
						point=0;
						resetAll();
						currentItem=Items.get(0);		
								
				
						//some repetittion					
						toAdd=currentItem;
						toAdd.flag=1;
						Items.set(toAdd.stateNum,toAdd);
					}
					if(literal(currentItem)){
						if(point==currentLine.length()){mark=point;resetAll();//System.out.println("XD");
						}	//scenario where it is a match but there is no more string to check
						else if(currentItem.ch.equals(String.valueOf(currentLine.charAt(point)))){
							Possible.add(Items.get(currentItem.pointer1));					
							point++;
							resetAll();						
						}
						else if(currentItem.ch.equals("π")){
							//wildcard is a match on anything
							Possible.add(Items.get(currentItem.pointer1));					
							point++;
							resetAll();	
						}	
						else{
							//System.out.println("not match, trying to match :"+currentItem.ch+" and "+String.valueOf(currentLine.charAt(point)));
						}										
					}
					else{
						//System.out.println("d");
						if(Items.get(currentItem.pointer1).flag!=1){
						Current.add(Items.get(currentItem.pointer1));}
						if(Items.get(currentItem.pointer2).flag!=1 && Items.get(currentItem.pointer2).stateNum!=Items.get(currentItem.pointer1).stateNum){
						Current.add(Items.get(currentItem.pointer2));}
					}	
				}
				else if(Current.size()<=0 && Possible.size()>0){//System.out.println("113");
					for(Item i : Possible){
						Current.add(i);
					}
					Possible.subList(0, Possible.size()).clear();
				}
				else{//System.out.println("119");
					//this is for when you have no states in current and possiblem, therefore you must increment the pointers
					mark++;
					point=mark;

					resetAll();
					currentItem=Items.get(0);			
					Current.add(currentItem);	
				}

				if(mark==currentLine.length()){		//System.out.println("129");		
					currentLine=textReader.readLine();
					if(currentLine==null){
						return;
					}
					currentItem=Items.get(0);
					Current.add(currentItem);
					mark=0;
					point=0;
				}//MIGHT NEED ONE FOR POINT == LENGTH
			}
				
				
		}
		catch(Exception e){
		
			System.out.println(e);
		
		}
	
	}	

	public Item pop(ArrayList<Item>  a){

		Item b=a.get(a.size()-1);
		a.remove(a.size()-1);
		return b;
	}

	public boolean literal(Item i){
		if(i.ch.equals("µ")||i.ch.equals("")){		
			return false;
		}
		else{
			return true;
		}
	}

	public void resetAll(){	
		for(Item i:Items){
			i.flag=0;
		}
	}

	ArrayList<Item> Items = new ArrayList<Item>();
	ArrayList<Item> Current = new ArrayList<Item>();
	ArrayList<Item> Possible = new ArrayList<Item>();
	
	public class  Item{	

		public Item(int a, String b, int c, int d){		
			stateNum=a;
			ch=b;
			pointer1=c;
			pointer2=d;	
		}		
		
		int stateNum;
		String ch;
		int pointer1;
		int pointer2;		
		//1=visited 0 =not visited
		int flag= 0;	
	}

}


