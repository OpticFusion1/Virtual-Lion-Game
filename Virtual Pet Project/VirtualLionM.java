
public class VirtualLionM {
    
    //creating a variable from the VirtualPetFace class
    VirtualPetFace face = new VirtualPetFace();
    
    String name;
    //attributes for immediate needs; default is 100
    int fatigue;
    int health;
    int thirst;
    int hunger;
    double happiness;//depends on the other above attributes
    //attribute for age of the lion
    int age;
    //helpful for updateAge method
    int calls;
    //states or moods
    String mood;
    
    boolean hasMate;
    boolean isDead;
    
    public VirtualLionM(String n){
        name = n;
        fatigue = 100; //default amounts
        health = 100;
        thirst = 100;
        hunger = 100;
        
        //happiness is the averge of the other traits besides age
        happiness  = 100;
        
        age = 0;
        calls = 0;
        
        hasMate = false;
        isDead = false;
        updateImage("cubHappy", "lionHappy");
        face.setMessage("Hi, I'm " + name + " the lion!");
        face.setMessage("Get me to age ten with a mate and I'll be a winner!");
    }
    

    //randomly returns true depending on given prob; i.e. prob = 0.5 is 50%
    private boolean chance(double prob){
        double rand = Math.random();
        return (rand <= prob);
    }
    
    //updates age based on amount of calls; makes lion die from age, thirst, health, or hunger
    private void updateStats(){
        age = calls / 5; //every five calls is equivalent to a year
        if (age == 10 && hasMate == true){ //condition for winning game
            isDead = true;
            resetLion();
            face.setImage("lionWins");
            face.setMessage("Congratulations! Your lion made it through life succesfully.");
            resetLion();
        } else if (age == 10 || hunger <= 0 || health <= 0 || thirst <= 0){//condition for losing 
            isDead = true; //add something that resets code, message
            face.setMessage("Your lion died!");
            resetLion();
        }
    }
    
    //updates mood based on happiness; if health, hunger, or thirst fall below 30 the lion gets sad
    private void updateMood(){
        happiness = (health + hunger + fatigue + thirst) / 4.0;//determines happiness level
        if (happiness >= 75 && health > 30 && hunger > 30 && thirst > 30){
            mood = "happy"; //add face and message
        } else if (happiness < 75&&happiness>=50&&health>30&&hunger>30&&thirst>30){
            mood = "O.K.";
        } else if (hunger <= 30){
            mood = "hungry";
        } else if (fatigue <= 30){
            mood = "tired";
        } else if (thirst <= 30){
            mood = "thirsty";
        } else if (health <= 30){
            mood = "in pain";
        }
    }
    
    //method that updates the image based on the lion's age
    private void updateImage(String cubPic, String lionPic){
        if (age <= 5){
            face.setImage(cubPic);
        } else {
            face.setImage(lionPic);
        }
    }
    
    //method that allows the lion to eat if it succeeds in hunting
    public void hunt(){
        if (fatigue <= 10){
            face.setMessage("I'm too tired!");
            sleep();
        } else {
            calls++;
            fatigue -= 10; //hunting makes it tired every time
            thirst -= 15; //makes it thirsty by default
            if (chance(0.7) == true ){ //lion gets food
                hunger += 20;
                health +=10;
                updateImage("cubEating", "lionEating");
                updateMood();
                face.setMessage("Yay, food!\nI'm "+ mood + "!");
            } else { //lion injured; no food
                hunger -= 10;
                health -= 10;
                updateImage("cubDanger","lionDanger");
                updateMood();
                face.setMessage("Aw, I didn't get my food!\nI'm "+ mood + "!");
            } 
            
            updateStats();
        }
    }
    
    //method that gives the lion a mate if it wins a fight
    public void fightForMate(){
        if (age >= 5){//must be adult lion to mate
            if (fatigue <= 30){
                face.setMessage("I'm too tired!");
                sleep();
            } else {
                calls++;
                fatigue -= 30;//will get tired by default
                thirst -= 20;//will get thirsty by default
                hunger -= 10;//makes it more hungry
                if (chance(0.5) == true){
                    hasMate = true;
                    face.setImage("lionWMate");
                    updateMood();
                    face.setMessage("I got a mate!\nI'm "+ mood + "!");
                } else{
                    health -= 20;
                    face.setImage("lionFighting");
                    updateMood();
                    face.setMessage("I lost and didn't get a mate!\nI'm "+ mood + "!");
                }
                
                updateStats();
            }
        } else {
            face.setMessage("I'm too young!");
            face.setImage("cubHungry");
        }
    }
    
    //allows the lion to replenish thirst levels at little consequence
    public void drink(){
        calls ++;
        //if(chance(0.8) == true){//if it finds water (usually does)
            thirst += 30;
            updateImage("cubDrinking", "lionDrinking");
            updateMood();
            face.setMessage("*slurp... slurp*\nI'm "+ mood + "!");
        //} else {
        //    thirst -= 15;
        //    updateImage("cubDanger", "lionDanger");
        //    updateMood();
        //    face.setImage("Oh, no! Someone else got to drink first!\nI'm "+ mood + "!");
        //}
        
        updateStats();
    }
    
    //sleep method drains hunger and thirst but replenishes fatigue and health
    public void sleep(){
        calls++;
        hunger -= 15;
        thirst -= 10;
        fatigue += 30;
        health += 10;
        updateMood();
        updateStats();
        updateImage("cubSleepy","lionSleepy");
        face.setMessage("Zzzzzzzz.\nI'm "+ mood + "!");
    }
    
    //raises happiness level, but only until the next call since happiness is
    //dependent on the other attributes
    public void play(){
        if (fatigue <= 10){
            sleep();
            face.setMessage("I'm too tired!");
        } else {
            calls++;
            fatigue -= 10;
            thirst -= 10;
            hunger -= 10;
            updateMood();
            happiness += 30;//after updateMood so that the hapiness points are added
            //regardless of average of fatigue, hunger, thirst and health
            updateStats();
            updateImage("cubPlaying", "lionsPlaying");
            face.setMessage("Derp!\nI'm "+ mood + "!");
        }
    }
    
    public void showStats(){
        face.setMessage("My name's "+name+" and I am "+age+" years old.");
        face.setMessage("Stats:\nFatigue: "+fatigue+"\nHealth: "+health+"\nThirst: "+thirst+"\nHunger: "+hunger);
        face.setMessage("happines: " + happiness);
    }

    //reverts the code to its original settings
    public void resetLion(){
        if(age != 10 || hasMate != true){//if player won, keeps winning image displayed
            face.setImage("cubHappy");
        }
        fatigue = 100;
        health = 100;
        thirst = 100;
        hunger = 100;
        age = 0;
        calls = 0;
        hasMate = false;
        isDead = false;
        face.setMessage("Here's a new lion.");
    }
    
}