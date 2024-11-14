package multiuserdungeon;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import com.fasterxml.jackson.databind.*;

import multiuserdungeon.inventory.BuffStat;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.inventory.elements.Bag;
import multiuserdungeon.inventory.elements.Food;
import multiuserdungeon.inventory.elements.Weapon;
import multiuserdungeon.inventory.elements.Armor;
import multiuserdungeon.inventory.elements.Buff;

public class Items {

    private String filename = "data\\itemsDB.json";
    private static Items instance = null;
    private Map<Integer, InventoryElement> items = new HashMap<Integer,InventoryElement>();
    
    private Items() throws IOException{
        load();
    }

    public static Items getInstance() throws IOException {
        if(instance == null){
            instance = new Items();
        }
        return instance;
    }

    public void load() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<HashMap<String, Object>> arlData = mapper.readValue(new File(filename), ArrayList.class);
        for(HashMap<String, Object> itemMap : arlData){
            if(itemMap.containsKey("defense")){
                InventoryElement item = new Armor( 
                    (String) itemMap.get("name"),
                    (String) itemMap.get("description"),
                    (int) itemMap.get("goldValue"),
                    (int) itemMap.get("defense")
                );
                items.put((int)itemMap.get("id"), item);
            }
            else if (itemMap.containsKey("capacity")){
                InventoryElement item = new Bag( 
                    (String) itemMap.get("name"),
                    (String) itemMap.get("description"),
                    (int) itemMap.get("goldValue"),
                    (int) itemMap.get("capacity")
                );
                items.put((int)itemMap.get("id"), item);
            }
            else if(itemMap.containsKey("stat")){
                InventoryElement item = new Buff( 
                    (String) itemMap.get("name"),
                    (String) itemMap.get("description"),
                    (int) itemMap.get("goldValue"),
                    (BuffStat) BuffStat.valueOf((String)itemMap.get("stat")),
                    (int) itemMap.get("statAmount")

                );
                items.put((int)itemMap.get("id"), item);
            }
            else if(itemMap.containsKey("health")){
                InventoryElement item = new Food( 
                    (String) itemMap.get("name"),
                    (String) itemMap.get("description"),
                    (int) itemMap.get("goldValue"),
                    (int) itemMap.get("health")
                );
                items.put((int)itemMap.get("id"), item);
            }
            else if(itemMap.containsKey("attack")){
                InventoryElement item = new Weapon( 
                    (String) itemMap.get("name"),
                    (String) itemMap.get("description"),
                    (int) itemMap.get("goldValue"),
                    (int) itemMap.get("attack")
                );
                items.put((int)itemMap.get("id"), item);
            }
        }
    }

    /**
     * instantiate new Inventory Element given item's id
     * @param id
     * @return InventoryElement
     */
    public InventoryElement getItem(int id){
        if (items.containsKey(id)) {
            InventoryElement item = items.get(id);
            if(item instanceof Armor){
                return new Armor((Armor)item);
            }
            if(item instanceof Bag){
                return new Bag((Bag)item);
            }
            else if(item instanceof Buff){
                return new Buff((Buff)item);
            }
            else if(item instanceof Food){
                return new Food((Food)item);
            }
            else if(item instanceof Food){
                return new Food((Food)item);
            }
            else if(item instanceof Weapon){
                return new Weapon((Weapon)item);
            }
        }
        return null;
    }

    public InventoryElement getRandomItem(){
        Random random = new Random();
        Object[] itemIDs = (items.keySet()).toArray();
        int randomtID = (int) itemIDs[random.nextInt(itemIDs.length)];
        return getItem(randomtID);
    }

    public Map<Integer, InventoryElement> getItems(){
        return items;
    }

    public List<InventoryElement> getRandomList(int amount){
        List<InventoryElement> randomList = new ArrayList<InventoryElement>();
        for(int i = 0; i < amount; i++){
            randomList.add(getRandomItem());
        }
        return randomList;
    }
}
