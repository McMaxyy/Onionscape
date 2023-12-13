package scenes;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;

import player.Player;
import storage.Storage;

public class SlotMinigame implements Screen{
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Game game;
	private GameScreen gameScreen;
	private Storage storage;
	private Random rand = new Random();
	private Label[][] symbolMatrix = new Label[3][5];
	private String[] symbols = {"A", "7", "X", "Y", "Z", "Book", "Skull", "Gun", "Scatter"};
	private TextButton spin;
	private int maxMultiplier = 15, totalWin;
	private ShapeRenderer shapeRenderer;
	private Label coins, winnings;
	
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
		
		createComponents();
	}
	
	private void newSpin() {
		if(symbolMatrix[0][0] != null)
			clearMatrix();
		
		int x = 0;
		int y = 0;
		
		for (int i = 0; i < 3; i++) {
		    for (int j = 0; j < 5; j++) {
		        symbolMatrix[i][j] = new Label(randomSymbol(), storage.labelStyleBig);
		        symbolMatrix[i][j].setSize(50, 50);
		        symbolMatrix[i][j].setPosition(vp.getWorldWidth() / 6 + x, vp.getWorldHeight() / 1.3f - y, Align.center);  // Adjust the position calculation
		        x += 250;
		        
		        stage.addActor(symbolMatrix[i][j]);
		    }
		    x = 0;
		    y += 250;
		}
		
		checkCombinations();
	}
	
	private boolean isSymbolEqual(int row, int col, String symbol) {
	    // Check if row and col are within bounds
	    if (row >= 0 && row < symbolMatrix.length && col >= 0 && col < symbolMatrix[0].length) {
	        return symbolMatrix[row][col] != null && symbolMatrix[row][col].getText().toString().equals(symbol);
	    } else {
	        return false;
	    }
	}

	private void checkCombinations() {
		totalWin = 0;
	    int minNeighboursOnPayline = 3;

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
	                addWinnings(neighboursOnPayline, symbol);
	            }
	        }
	    }
	    
	    if(totalWin > 0) {
	    	Player.gainCoins(totalWin);
	    	coins.setText("Coins: " + Player.getCoins());
	    	winnings.setText("Winnings: " + totalWin);
	    }
	    checkScatters();
	}
	
	private void addWinnings(int amount, String symbol) {
		switch(symbol) {
		case "A":
		case "X":
		case "Y":
		case "Z":
			totalWin += amount * (maxMultiplier / 4);
			break;
		case "7":
			totalWin += amount * (maxMultiplier / 3);
			break;
		case "Skull":
		case "Gun":
			totalWin += amount * (maxMultiplier / 2);
			break;
		case "Book":
			totalWin += amount * maxMultiplier;
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

	    if (scatterCount >= 3) {
	        Gdx.app.log("Combination", "Scatter combination: " + scatterCount + " Scatter symbols");
	    }
	}
	
	private void clearMatrix() {
	    for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < 5; j++) {
	            Label labelToRemove = symbolMatrix[i][j];
	            
	            if (labelToRemove != null) {
	                stage.getRoot().removeActor(labelToRemove);
	                labelToRemove.clear();
	            }
	            
	            symbolMatrix[i][j] = null;
	        }
	    }
	}

	
	private String randomSymbol() {
		int x = rand.nextInt(19);
		
		if(x >= 0 && x <=2)
			return symbols[0];
		else if(x >= 3 && x <=5)
			return symbols[1];
		else if(x >= 6 && x <=8)
			return symbols[2];
		else if(x >= 9 && x <=11)
			return symbols[3];
		else if(x >= 12 && x <=14)
			return symbols[4];
		else if(x == 15)
			return symbols[5];
		else if(x == 16)
			return symbols[6];
		else if(x == 17)
			return symbols[7];
		else if(x == 18)
			return symbols[8];
		else
			return null;
	}
	
	private void createComponents() {
		spin = new TextButton("Spin (15 coins)", storage.buttonStyle);
		spin.setColor(Color.LIGHT_GRAY);
		spin.addListener(new ClickListener() {
			@Override
		    public void clicked(InputEvent event, float x, float y) { 
				if(Player.getCoins() >= 15) {
					Player.loseCoins(15);
					coins.setText("Coins: " + Player.getCoins());
					winnings.setText("");
					newSpin();
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
	}
	
	private void drawLinesForPayline(int paylineIndex) {
	    shapeRenderer.begin(ShapeType.Line);
	    shapeRenderer.setColor(Color.RED); // Set the line color as needed
	    Gdx.gl.glLineWidth(3);  // Set the line width as needed

	    int[][] payline = paylines[paylineIndex];

	    for (int j = 0; j < payline.length - 1; j++) {
	        int startX = payline[j][1];
	        int startY = payline[j][0];
	        int endX = payline[j + 1][1];
	        int endY = payline[j + 1][0];

	        float startXPos = vp.getWorldWidth() / 6 + startX * 250 + symbolMatrix[0][0].getWidth() / 2 - 25;
	        float startYPos = vp.getWorldHeight() / 1.3f - startY * 250 - symbolMatrix[0][0].getHeight() / 2f + 75;
	        float endXPos = vp.getWorldWidth() / 6 + endX * 250 + symbolMatrix[0][0].getWidth() / 2f - 25;
	        float endYPos = vp.getWorldHeight() / 1.3f - endY * 250 - symbolMatrix[0][0].getHeight() / 2f + 75;

	        shapeRenderer.line(startXPos, startYPos, endXPos, endYPos);
	    }

	    shapeRenderer.end();
	    Gdx.gl.glLineWidth(1);  // Reset the line width
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
	        if (hasWinningCombination(i)) {
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
