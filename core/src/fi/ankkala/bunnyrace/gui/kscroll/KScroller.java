package fi.ankkala.bunnyrace.gui.kscroll;

public class KScroller {
	  public float speed=0;
	  private float start;
	  public float decay;
	  private long decay_start;
	  private int decay_maxmillis=5000;
	  
	  public KScroller() {
		  
	  }

	  // decrease scrolling speed once mouse is released
	  public float slowdown()
	  {
	    float d=0;
	    // if there is still some scrolling energy
	    if(this.decay>0)
	    {
	      //i tried a few slowing formulas
	    	
	      this.decay=1-inverse(System.currentTimeMillis()-this.decay_start,this.decay_maxmillis);
	      //this.decay=1-sigmoid(System.currentTimeMillis();-this.decay_start,this.decay_maxmillis);
	      //this.decay=this.decay*.9;
	      
	      d=this.speed*this.decay;
	      
	    }
	    return d;
	  } //slowdown

	  public void begin(int x)
	  {
	    // the initial scrolling speed is the speed it was being dragged
	    this.speed = x-this.start;
	    this.start = x;
	  } //begin

	  public void grab(int x)
	  {
	    // while grabbing is occuring, the scrolling link to mouse movement
	    this.start = x;
	    this.decay = 0;
	    this.speed = 0;
	  } //grab

	  public void startslowdown()
	  {
	    // the mouse has been released and we can start slowing the scroll
	    // the speed starts at 100% of the current scroll speed
	    // record the time so we can calualte the decay rate
		if (this.speed == 0) {
			return;
		}
	    this.decay=1;
	    this.decay_start=System.currentTimeMillis();
	  } //startslodown
	  
	  private float inverse(float v,float vmax)
	  {   
		   //double i=v/vmax;
		   double r=1+Math.pow((1-v/vmax),3)*-1; 
		   return (float)r;
		}//inverse
}
