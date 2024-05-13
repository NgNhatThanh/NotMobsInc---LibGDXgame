    package screens;
    
    import btck.com.GameManager;
    import btck.com.MyGdxGame;
    import btck.com.model.constant.Constants;
    import btck.com.model.constant.GameState;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.graphics.Texture;
    
    public class GameOverScreen  implements Screen {
        public final int gameOverWidth = 400;
        public final int gameOverHeight = 100;
        public final int tryAgainWidth = 300;
        public final int tryAgainHeight = 70;
        public int WIDTH = Gdx.graphics.getWidth();
        public int HEIGHT = Gdx.graphics.getHeight();
        MyGdxGame myGdxGame;
    
    
        Texture gameOver;
        Texture tryAgainInactive;
        Texture tryAgainActive;
        public GameOverScreen(MyGdxGame myGdxGame){
            System.out.println(WIDTH);
            System.out.println(HEIGHT);
            this.myGdxGame = myGdxGame;
            gameOver = new Texture(Constants.gameOverImgPath);
            tryAgainActive = new Texture(Constants.tryAgainIconActivePath);
            tryAgainInactive = new Texture(Constants.tryAgainIconInactivePath);
        }
    
        @Override
        public void show() {
    
        }
    
        @Override
        public void render(float delta) {
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());

            Gdx.gl.glClearColor(0f, 0f, 0f, 1); // Màu xám trung bình
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            myGdxGame.batch.begin();
    
            int gameOverX = (WIDTH - gameOverWidth)/2;
            int gameOverY = (HEIGHT - gameOverHeight)/2;
//            System.out.println(gameOverX + " " + gameOverY);

            myGdxGame.batch.draw(gameOver, gameOverX, gameOverY, gameOverWidth, gameOverHeight);

            int tryAgainX =  (WIDTH - tryAgainWidth)/2;;
            int tryAgainY = gameOverY - 70;
    
            if(Gdx.input.getX() < tryAgainX + tryAgainWidth && Gdx.input.getX() > tryAgainX &&
                    HEIGHT - Gdx.input.getY() < tryAgainHeight + tryAgainY &&
                    HEIGHT - Gdx.input.getY() > tryAgainY) {
                myGdxGame.batch.draw(tryAgainActive, tryAgainX, tryAgainY, tryAgainWidth, tryAgainHeight);
                if(Gdx.input.isTouched()){
                    myGdxGame.setScreen(new MainMenuScreen(myGdxGame));
                }
            }
            else{
                myGdxGame.batch.draw(tryAgainInactive, tryAgainX, tryAgainY, tryAgainWidth, tryAgainHeight);
            }



            myGdxGame.batch.end();
        }
    
        @Override
        public void resize(int width, int height) {
            this.WIDTH = width;
            this.HEIGHT = height;
        }
    
        @Override
        public void pause() {
    
        }
    
        @Override
        public void resume() {
    
        }
    
        @Override
        public void hide() {
    
        }
    
        @Override
        public void dispose() {
            this.gameOver.dispose();
            this.tryAgainActive.dispose();
            this.tryAgainInactive.dispose();
        }
    }
    
