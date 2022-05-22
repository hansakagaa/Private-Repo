import java.util.*;
class Stack{
	private int[] dataArray;
	private int nextIndex;
	
	Stack(int size){
		dataArray=new int[size];
		nextIndex=0;
	}
	public void push(int data){
		if(isFull()){
			System.out.println("Stack is full...");
		}else{
			dataArray[nextIndex]=data;
			nextIndex++;
		}
	}
	public void printStack(){
		System.out.print("[");
		for (int i = nextIndex-1; i >=0 ; i--){
			System.out.print(dataArray[i]+", ");
		}
		System.out.println(isEmpty() ? "empty]":"\b\b]");
	}
	public void pop(){
		if(isEmpty()){System.out.println("Stack is empty...");}
		else{nextIndex--;}
		//isEmpty() ? System.out.println("[empty]") : nextIndex-- ;
	}
	public int size(){
		return nextIndex;
	}
	private boolean isEmpty(){
		return nextIndex==0;
	}
	public boolean isFull(){
		return nextIndex>=dataArray.length;
	}
	public void clear(){
		nextIndex=0;
	}
}
class Example{
	public static void main(String args[]){
		Stack s1=new Stack(5);
		System.out.println("Size of the stack : "+s1.size()); //0
		s1.printStack(); //[empty]
		s1.push(10);
		s1.push(20);
		s1.push(30);
		s1.push(40);
		s1.push(50);
		s1.printStack(); //[50,40,30,20,10]
		System.out.println("Size of the stack : "+s1.size()); //5
		
		s1.push(60); //Stack is full....
		s1.printStack(); //[50,40,30,20,10]
		
		s1.clear();
		System.out.println("Size of the stack : "+s1.size()); //0
		s1.printStack(); //[empty]
		
		s1.pop(); //Stack is empty.....
	}
}
