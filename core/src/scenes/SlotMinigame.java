package scenes;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;

import player.Player;
import storage.Storage;

@SuppressWarnings("unused")
public class SlotMinigame implements Screen{
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Game game;
	private GameScreen gameScreen;
	private Storage storage;
	private Random rand = new Random();
	private Image[][] symbolMatrix = new Image[3][5];
	private String[] symbols = {"A", "10", "J", "K", "Q", "Book", "Skull", "Gun", "Scatter"};
	private TextButton spin, higher, lower, backBtn;
	private int multiplier = 15, totalWin, freeSpinsLeft;
	private ShapeRenderer shapeRenderer;
	private Label coins, winnings, betSize, freeSpinsLbl;
	private Texture tenT, aT, bookT, gunT, jT, kT, qT, scatterT, skullT;	
	private float hitRate = 69.420f;
	private boolean succHit;
	private int[][][] paylines = {
            {{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}},  // Line 1
            {{1, 0}, {0, 1}, {0, 2}, {0, 3}, {1, 4}},  // Line 2
            {{1, 0}, {1, 1}, {1, 2}, {1, 3}, {1, 4}},  // Line 3
            {{1, 0}, {2, 1}, {2, 2}, {2, 3}, {1, 4}},  // Line 4
            {{2, 0}, {2, 1}, {2, 2}, {2, 3}, {2, 4}},  // Line 5
            {{0, 0}, {1, 1}, {2, 2}, {1, 3}, {0, 4}},  // Line 6
            {{2, 0}, {1, 1}, {0, 2}, {1, 3}, {2, 4}},  // Line 7
            {{0, 0}, {0, 1}, {1, 2}, {2, 3}, {2, 4}},  // Line 8
            {{2, 0}, {2, 1}, {1, 2}, {0, 3}, {0, 4}},  // Line 9
            {{0, 0}, {1, 0}, {2, 0}},  // Line 10
            {{0, 1}, {1, 1}, {2, 1}},  // Line 11
            {{0, 2}, {1, 2}, {2, 2}},  // Line 12
            {{0, 3}, {1, 3}, {2, 3}},  // Line 13
            {{0, 4}, {1, 4}, {2, 4}},  // Line 14
    };
	
	public SlotMinigame(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = storage.skin;
		storage.createFont();
		shapeRenderer = new ShapeRenderer();
		
		loadTextures();
		createComponents();		
	}
	
	private void loadTextures() {
		tenT = Storage.assetManager.get("slots/10.png", Texture.class);
		tenT.setFilter(TextureFilter.MipMap,TextureFilter.Nearest); 
		aT = Storage.assetManager.get("slots/A.png", Texture.class);
		aT.setFilter(TextureFilter.MipMap,TextureFilter.Nearest); 	
		bookT = Storage.assetManager.get("slots/Book.png", Texture.class);
		bookT.setFilter(TextureFilter.MipMap,TextureFilter.Nearest); 	
		gunT = Storage.assetManager.get("slots/Gun.png", Texture.class);
		gunT.setFilter(TextureFilter.MipMap,TextureFilter.Nearest); 	
		jT = Storage.assetManager.get("slots/J.png", Texture.class);
		jT.setFilter(TextureFilter.MipMap,TextureFilter.Nearest); 	
		kT = Storage.assetManager.get("slots/K.png", Texture.class);
		kT.setFilter(TextureFilter.MipMap,TextureFilter.Nearest); 	
		qT = Storage.assetManager.get("slots/Q.png", Texture.class);
		qT.setFilter(TextureFilter.MipMap,TextureFilter.Nearest); 	
		skullT = Storage.assetManager.get("slots/Skull.png", Texture.class);
		skullT.setFilter(TextureFilter.MipMap,TextureFilter.Nearest); 	
		scatterT = Storage.assetManager.get("slots/Scatter.png", Texture.class);
		scatterT.setFilter(TextureFilter.MipMap,TextureFilter.Nearest); 	
	}

	private void newSpin() {
	    if (symbolMatrix[0][0] != null)
	        clearMatrix();

	    int x = 0;
	    int y = 0;

	    for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < 5; j++) {
	            // Create Image instances and set the texture based on the random symbol
	            Image newImage = new Image();
	            newImage.setDrawable(new TextureRegionDrawable(getSymbolTexture(randomSymbol(), newImage)));
	            newImage.setSize(150, 150);
	            newImage.setPosition(vp.getWorldWidth() / 6 + x, vp.getWorldHeight() / 1.3f - y, Align.center);

	            symbolMatrix[i][j] = newImage;
	            stage.addActor(symbolMatrix[i][j]);

	            x += 200;
	        }
	        x = 0;
	        y += 200;
	    }

	    checkCombinations();
	}
	

	private Texture getSymbolTexture(String symbol, Image img) {
        switch (symbol) {
            case "A":
            	img.setName("A");
                return aT;
            case "10":
            	img.setName("10");
                return tenT;
            case "J":
            	img.setName("J");
                return jT;
            case "K":
            	img.setName("K");
                return kT;
            case "Q":
            	img.setName("Q");
                return qT;
            case "Book":
            	img.setName("Book");
                return bookT;
            case "Skull":
            	img.setName("Skull");
                return skullT;
            case "Gun":
            	img.setName("Gun");
                return gunT;
            case "Scatter":
            	img.setName("Scatter");
                return scatterT;
            default:
                return null;
        }
    }
	
	private boolean isSymbolEqual(int row, int col, String symbol) {
	    // Check if row and col are within bounds
	    if (row >= 0 && row < symbolMatrix.length && col >= 0 && col < symbolMatrix[0].length) {
	        Image image = symbolMatrix[row][col];
	        return image != null && image.getName() != null && image.getName().equals(symbol);
	    } else {
	        return false;
	    }
	}
	
	private void checkCombinations() {
		totalWin = 0;
	    int minNeighboursOnPayline = 3;
	    float hit = rand.nextFloat(100);

	    for (String symbol : symbols) {
	        for (int i = 0; i < paylines.length; i++) {
	            int neighboursOnPayline = countNeighboursOnPayline(i, symbol);

	            if (symbol.equals("Book") || symbol.equals("Skull") || symbol.equals("Gun"))
	                minNeighboursOnPayline = 2;
	            else
	                minNeighboursOnPayline = 3;

	            if (neighboursOnPayline >= minNeighboursOnPayline) {
	                Gdx.app.log("Combination", "Payline " + (i + 1) + " has " + neighboursOnPayline + " neighbouring " + symbol + " symbols");
	            
	                // Winnings
	                if(hit <= hitRate) {
	                	addWinnings(neighboursOnPayline, symbol);
	                	succHit = true;
	                }
	            }
	        }
	    }
	    
	    if(totalWin > 0 && succHit) {
	    	Player.gainCoins(totalWin);
	    	coins.setText("Coins: " + Player.getCoins());
	    	winnings.setText("Winnings: " + totalWin);
	    }
	    checkScatters();
	}
	
	private void addWinnings(int amount, String symbol) {
		float bet = (float)multiplier;
		
		switch(symbol) {
		case "A":
		case "J":
		case "K":
		case "Q":			
			if(freeSpinsLeft > 0)
				totalWin += amount * (bet / 2.5f);
			else
				totalWin += amount * (bet / 3.3f);
			break;
		case "10":
			if(freeSpinsLeft > 0)
				totalWin += amount * (bet / 1.8f);
			else
				totalWin += amount * (bet / 2.2f);
			break;
		case "Skull":
		case "Gun":
			if(freeSpinsLeft > 0)
				totalWin += amount * bet;
			else
				totalWin += amount * (bet / 2) + amount;
			break;
		case "Book":
			if(freeSpinsLeft > 0)
				totalWin += amount * bet * 2;
			else
				totalWin += amount * bet;
			break;
		}
	}
	
	private boolean hasWinningCombination(int paylineIndex) {
	    int minNeighboursOnPayline = 3;

	    // Iterate over symbols and check if any of them has a winning combination on the specified payline
	    for (String symbol : symbols) {
	        int neighboursOnPayline = countNeighboursOnPayline(paylineIndex, symbol);

	        if ((symbol.equals("Book") || symbol.equals("Skull") || symbol.equals("Gun")) && neighboursOnPayline >= 2) {
	            return true;
	        } else if (neighboursOnPayline >= minNeighboursOnPayline) {
	            return true;
	        }
	    }

	    // No winning combination found for any symbol on this payline
	    return false;
	}


	private int countNeighboursOnPayline(int paylineIndex, String symbol) {
	    int neighboursOnPayline = 0;

	    int[][] payline = paylines[paylineIndex];

	    for (int j = 0; j < payline.length; j++) {
	        int row = payline[j][0];
	        int col = payline[j][1];

	        // Check if the symbol at the current position matches the target symbol
	        if (isSymbolEqual(row, col, symbol)) {
	            // Check for neighbouring symbols on the payline
	            boolean hasNeighboursLeft = isPositionOnPayline(row, col - 1, payline) && isSymbolEqual(row, col - 1, symbol);
	            boolean hasNeighboursRight = isPositionOnPayline(row, col + 1, payline) && isSymbolEqual(row, col + 1, symbol);
	            boolean hasNeighboursAbove = isPositionOnPayline(row - 1, col, payline) && isSymbolEqual(row - 1, col, symbol);
	            boolean hasNeighboursBelow = isPositionOnPayline(row + 1, col, payline) && isSymbolEqual(row + 1, col, symbol);
	            boolean hasNeighboursAboveLeft = isPositionOnPayline(row - 1, col - 1, payline) && isSymbolEqual(row - 1, col - 1, symbol);
	            boolean hasNeighboursAboveRight = isPositionOnPayline(row - 1, col + 1, payline) && isSymbolEqual(row - 1, col + 1, symbol);
	            boolean hasNeighboursBelowLeft = isPositionOnPayline(row + 1, col - 1, payline) && isSymbolEqual(row + 1, col - 1, symbol);
	            boolean hasNeighboursBelowRight = isPositionOnPayline(row + 1, col + 1, payline) && isSymbolEqual(row + 1, col + 1, symbol);

	            if (hasNeighboursLeft || hasNeighboursRight || hasNeighboursAbove || hasNeighboursBelow ||
	                hasNeighboursAboveLeft || hasNeighboursAboveRight || hasNeighboursBelowLeft || hasNeighboursBelowRight) {
	                neighboursOnPayline++;
	            }
	        }
	    }

	    return neighboursOnPayline;
	}

	private boolean isPositionOnPayline(int row, int col, int[][] payline) {
	    for (int j = 0; j < payline.length; j++) {
	        if (row == payline[j][0] && col == payline[j][1]) {
	            return true;
	        }
	    }
	    return false;
	}

	private void checkScatters() {
	    int scatterCount = 0;

	    for (int row = 0; row < 3; row++) {
	        for (int col = 0; col < 5; col++) {
	            if (isSymbolEqual(row, col, "Scatter")) {
	                scatterCount++;
	            }
	        }
	    }
	    
	    if(freeSpinsLeft <= 0) {
	    	if(scatterCount == 3)
		    	freeSpinsLeft = 6;
		    else if(scatterCount == 4)
		    	freeSpinsLeft = 9;
		    else if(scatterCount > 4)
		    	freeSpinsLeft = 11;
	    }
	    else {
	    	if(scatterCount == 3)
		    	freeSpinsLeft += 6;
		    else if(scatterCount == 4)
		    	freeSpinsLeft += 9;
		    else if(scatterCount > 4)
		    	freeSpinsLeft += 11;
	    }	    
	    
	    if (scatterCount >= 3) {
	        Gdx.app.log("Combination", "Scatter combination: " + scatterCount + " Scatter symbols");	        
	        freeSpins(1);        
	    }
	}
	
	private void clearMatrix() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                Image imageToRemove = symbolMatrix[i][j];

                if (imageToRemove != null) {
                    stage.getRoot().removeActor(imageToRemove);
                    imageToRemove.clear();
                }

                symbolMatrix[i][j] = null;
            }
        }
    }
	
	private String randomSymbol() {
		int x = rand.nextInt(33);
		
		if(x >= 0 && x <=5)
			return symbols[0];
		else if(x >= 6 && x <=10)
			return symbols[1];
		else if(x >= 11 && x <=15)
			return symbols[2];
		else if(x >= 16 && x <=20)
			return symbols[3];
		else if(x >= 21 && x <=25)
			return symbols[4];
		else if(x == 26 || x == 27)
			return symbols[5];
		else if(x == 28 || x == 29)
			return symbols[6];
		else if(x == 30 || x == 31)
			return symbols[7];
		else if(x == 32)
			return symbols[8];
		else
			return null;
	}
	
	private void createComponents() {
		backBtn = new TextButton("Back", storage.buttonStyle);
		backBtn.setColor(Color.LIGHT_GRAY);
		backBtn.addListener(new ClickListener() {
			@Override
		    public void clicked(InputEvent event, float x, float y) {  
				gameScreen.switchToNewState(GameScreen.HOME);					
		    }});
		backBtn.setSize(150, 100);
		backBtn.setPosition(vp.getWorldWidth() / 40f, vp.getWorldHeight() / 1.25f);
		stage.addActor(backBtn);	
		
		spin = new TextButton("Spin", storage.buttonStyle);
		spin.setColor(Color.LIGHT_GRAY);
		spin.addListener(new ClickListener() {
			@Override
		    public void clicked(InputEvent event, float x, float y) { 
				succHit = false;
				
				if(Player.getCoins() >= multiplier && freeSpinsLeft <= 0) {
					Player.loseCoins(multiplier);
					coins.setText("Coins: " + Player.getCoins());
					winnings.setText("");
					newSpin();
				}
				else if(freeSpinsLeft > 0) {
					winnings.setText("");
					newSpin();
				}
				
				if(freeSpinsLeft > 0) {					
					freeSpinsLeft--;
					freeSpinsLbl.setText("Free spins: " + freeSpinsLeft);
					if(freeSpinsLeft == 0)
						freeSpins(0);
				}
		    }});
		spin.setSize(250, 100);
		spin.setPosition(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 10f);
		stage.addActor(spin);	
		
		coins = new Label("Coins: " + Player.getCoins(), storage.labelStyle);
		coins.setPosition(spin.getX() - 200, spin.getY());
		stage.addActor(coins);
		
		winnings = new Label("", storage.labelStyle);
		winnings.setPosition(spin.getX() + spin.getWidth() + 50, spin.getY());
		stage.addActor(winnings);
		
		betSize = new Label(String.valueOf(multiplier), storage.labelStyle);
		betSize.setPosition(vp.getWorldWidth() / 2f - spin.getWidth() - coins.getWidth(), vp.getWorldHeight() / 10f);
		stage.addActor(betSize);
		
		higher = new TextButton("+", storage.buttonStyle);
		higher.setColor(Color.LIGHT_GRAY);
		higher.addListener(new ClickListener() {
			@Override
		    public void clicked(InputEvent event, float x, float y) { 
				changeBet(0);
		    }});
		higher.setSize(50, 50);
		higher.setPosition(betSize.getX() + betSize.getWidth() * 1.5f, vp.getWorldHeight() / 10f);
		stage.addActor(higher);	
		
		lower = new TextButton("-", storage.buttonStyle);
		lower.setColor(Color.LIGHT_GRAY);
		lower.addListener(new ClickListener() {
			@Override
		    public void clicked(InputEvent event, float x, float y) { 
				changeBet(1);
		    }});
		lower.setSize(50, 50);
		lower.setPosition(betSize.getX() - betSize.getWidth() * 2, vp.getWorldHeight() / 10f);
		stage.addActor(lower);	
				
		freeSpinsLbl = new Label("", storage.labelStyle);
		freeSpinsLbl.setPosition(vp.getWorldWidth() / 1.3f, vp.getWorldHeight() / 1.5f);
		stage.addActor(freeSpinsLbl);
	}
	
	private void freeSpins(int x) {
		if(x == 0) {
			higher.setVisible(true);
			lower.setVisible(true);
			freeSpinsLbl.setVisible(false);
			hitRate = 69.420f;
		}
		else {
			hitRate = 77.5f;
			higher.setVisible(false);
			lower.setVisible(false);
			freeSpinsLbl.setVisible(true);
	        freeSpinsLbl.setText("Free spins: " + freeSpinsLeft);
		}
	}
	
	private void changeBet(int x) {
		if(x == 0) {
			switch(multiplier) {
			case 10:
				multiplier = 15;
				break;
			case 15:
				multiplier = 20;
				break;
			case 20:
				multiplier = 40;
				break;
			case 40:
				multiplier = 80;
				break;
			}
		}
		else {
			switch(multiplier) {
			case 15:
				multiplier = 10;
				break;
			case 20:
				multiplier = 15;
				break;
			case 40:
				multiplier = 20;
				break;
			case 80:
				multiplier = 40;
				break;
			}
		}
		
		betSize.setText(multiplier);
	}
	
	private void drawLinesForPayline(int paylineIndex) {
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        int[][] payline = paylines[paylineIndex];

        for (int j = 0; j < payline.length - 1; j++) {
            int startX = payline[j][1];
            int startY = payline[j][0];
            int endX = payline[j + 1][1];
            int endY = payline[j + 1][0];

            float startXPos = vp.getWorldWidth() / 6 + startX * 200 + symbolMatrix[0][0].getWidth() / 2 - 75;
            float startYPos = vp.getWorldHeight() / 1.3f - startY * 200 - symbolMatrix[0][0].getHeight() / 2f + 75;
            float endXPos = vp.getWorldWidth() / 6 + endX * 200 + symbolMatrix[0][0].getWidth() / 2f - 75;
            float endYPos = vp.getWorldHeight() / 1.3f - endY * 200 - symbolMatrix[0][0].getHeight() / 2f + 75;

            shapeRenderer.rectLine(startXPos, startYPos, endXPos, endYPos, 4f);
        }

        shapeRenderer.end();
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		stage.act();
		stage.draw();
		
		for (int i = 0; i < paylines.length; i++) {
	        if (hasWinningCombination(i) && succHit) {
	            drawLinesForPayline(i);	            
	        }
	    }
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
	    stage.dispose();
	    skin.dispose();
	    storage.font.dispose();
	    shapeRenderer.dispose(); // Dispose the ShapeRenderer
	}
}
