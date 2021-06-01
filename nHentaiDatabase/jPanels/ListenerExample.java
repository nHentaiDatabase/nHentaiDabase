package jPanels;

import java.util.ArrayList;
import java.util.List;

interface HelloListener {
    void someoneSaidHello();
}

public class ListenerExample {
	
	private List<HelloListener> listeners = new ArrayList<HelloListener>();

    public void addListener(HelloListener toAdd) {
        listeners.add(toAdd);
    }

    public void sayHello() {
        System.out.println("Hello!!");

        // Notify everybody that may be interested.
        for (HelloListener hl : listeners)
            hl.someoneSaidHello();
    }
}

abstract class Responder implements HelloListener {

	public abstract void someoneSaidHello();
}

class Test {
    public static void main(String[] args) {
    	ListenerExample exportPopUp = new ListenerExample();

    	exportPopUp.addListener(new Responder() {

			@Override
			public void someoneSaidHello() {
				// TODO Auto-generated method stub
				System.out.println("Moin");
			}
    		
    	});

    	exportPopUp.sayHello();  // Prints "Hello!!!" and "Hello there..."
    }
}