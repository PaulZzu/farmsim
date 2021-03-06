package farmsim.tasks;

import java.util.Hashtable;

import farmsim.resource.SimpleResource;
import farmsim.entities.agents.Agent;
import farmsim.entities.tileentities.crops.*;
import farmsim.inventory.Storage;
import farmsim.tiles.TileRegister;
import farmsim.util.Point;
import farmsim.world.World;
import farmsim.world.WorldManager;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * PlantTask requires an Agent to plant a seed on plowed dirt.
 * 
 * @author Leggy
 *
 */
public class PlantTask extends AgentRoleTask {

    private String plant;
    private static final int BASE_DURATION = 1000;
    private static final int PLOUGHED_DIRT =
            TileRegister.getInstance().getTileType("ploughed_dirt");
    private TextArea output;
    private static final String ID = "plant";

    private Storage seeds = WorldManager.getInstance().getWorld().getStorageManager().getSeeds();
    public PlantTask(Point point, World world, String plant, TextArea output) {
        super(point, BASE_DURATION, world, "Plant " + plant, ID);
        // Adjust duration depending on agent's level in required role
        this.duration *= defaultRoleLevelTimeModifier();
        this.plant = plant;
        this.output = output;
    }
    
    public PlantTask copy() {
    	return new PlantTask(location, world, plant, output);
    }

	@Override
    public void preTask() {
        SimpleResource seed = new SimpleResource(plant + " Seeds", new Hashtable<String, String>(), 1);
        seeds.removeItem(seed, 1);
    }

    @Override
    public void postTask() {
        switch (plant) {
            case "Sugarcane":
                SugarCane cane = new SugarCane(world
                        .getTile((int) location.getX(), (int) location.getY()));
                world.getTile((int) location.getX(), (int) location.getY())
                        .setTileEntity(cane);
                break;
            case "Banana":
                Banana banana = new Banana(world.getTile((int) location.getX(),
                        (int) location.getY()));
                world.getTile((int) location.getX(), (int) location.getY())
                        .setTileEntity(banana);
                break;
            case "Mango":
                Mango mango = new Mango(world.getTile((int) location.getX(),
                        (int) location.getY()));
                world.getTile((int) location.getX(), (int) location.getY())
                        .setTileEntity(mango);
                break;
            case "Apple":
                Apple apple = new Apple(world.getTile((int) location.getX(),
                        (int) location.getY()));
                world.getTile((int) location.getX(), (int) location.getY())
                        .setTileEntity(apple);
                break;
            case "Lettuce":
                Lettuce lettuce = new Lettuce(world
                        .getTile((int) location.getX(), (int) location.getY()));
                world.getTile((int) location.getX(), (int) location.getY())
                        .setTileEntity(lettuce);
                break;
            case "Pear":
                Pear pear = new Pear(world.getTile((int) location.getX(),
                        (int) location.getY()));
                world.getTile((int) location.getX(), (int) location.getY())
                        .setTileEntity(pear);
                break;
            case "Strawberry":
                Strawberry strawberry = new Strawberry(world
                        .getTile((int) location.getX(), (int) location.getY()));
                world.getTile((int) location.getX(), (int) location.getY())
                        .setTileEntity(strawberry);
                break;
            case "Corn":
                Corn corn = new Corn(world.getTile((int) location.getX(),
                        (int) location.getY()));
                world.getTile((int) location.getX(), (int) location.getY())
                        .setTileEntity(corn);
                break;
            case "Cotton":
                Cotton cotton = new Cotton(world.getTile((int) location.getX(),
                        (int) location.getY()));
                world.getTile((int) location.getX(), (int) location.getY())
                        .setTileEntity(cotton);
                break;
            case "Lemon":
                Lemon lemon = new Lemon (world
                        .getTile((int) location.getX(), (int) location.getY()));
                world.getTile((int) location.getX(), (int) location.getY())
                        .setTileEntity(lemon);
                break;            
        }
        if(output != null) {
	        if(plant.equals("Apple")) {
	            Platform.runLater(() -> { 
	                output.appendText("Planted an " + plant + " plant" + 
	                        System.getProperty("line.separator"));
	            });
	        } else {
	            Platform.runLater(() -> { 
	                output.appendText("Planted a " + plant + " plant" + 
	                        System.getProperty("line.separator"));
	            });
	        }
        }
    }

    @Override
    public boolean isValid() {
        if (!super.isValid()) {
            return false;
        }
        /*
         * Plant task is valid so long as the tile is ploughed and there is not
         * a plant there already.
         */
        boolean validity = tile.getTileType() == PLOUGHED_DIRT
                && tile.getTileEntity() == null;
        if(!validity && output != null) {
            Platform.runLater(() -> { 
                output.appendText("Field must be ploughed" + 
                        System.getProperty("line.separator"));
            });
        }
        return validity;
    }

    @Override
    public Agent.RoleType relatedRole() {
        return Agent.RoleType.FARMER;
    }

    @Override
    public int experienceGained() {
        return 2;
    }
    
    
}
