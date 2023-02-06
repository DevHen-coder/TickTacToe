

class Main{
    public static void main(String[] args){
        Game mainGame = new Game();
        while(mainGame.getIsRunning()){
            mainGame.start();
        }
        mainGame.cleanUp();
    }
}
