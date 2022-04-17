package com.andi.DungeonExplorer.actor.Inventory;


import com.andi.DungeonExplorer.world.Equipment.Equipment;
import com.andi.DungeonExplorer.world.Equipment.Weapon;
import com.andi.DungeonExplorer.world.InteractableItems.CrystalPiece;
import com.andi.DungeonExplorer.world.Equipment.Key;
import com.andi.DungeonExplorer.world.WorldObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Andi Li on 10/4/2017.
 */


public class Inventory extends Array{

    private Array<Slot> slots;

    public Inventory() {

        slots = new Array<Slot>(10);
        for (int i = 0; i < 10; i++) {
            slots.add(new Slot(null, 0));
        }



        // create a few random empty slots
        for (int i = 0; i < 3; i++) {
            Slot randomSlot = slots.get(MathUtils.random(0, slots.size - 1));
            randomSlot.take(randomSlot.getAmount());
        }
    }

    /**
     * checks if inventory is empty
     * @return
     */
    public boolean isNotEmpty() {
        for (Slot slot : slots) {
            if (slot.getObject() != null) {
               return true;
            }
        }
        return false;
    }

    /**
     * checks the inventory for a specfic object
     * @param piece
     * @return
     */
    public int checkCrystals(CrystalPiece piece) {
        int amount = 0;

        for (Slot slot : slots) {
            if (slot.getObject() instanceof CrystalPiece) {
                amount += slot.getAmount();
            }
        }

        return amount;
    }

    /**
     * gets the key in the inv based on the key you have
     * @param keyId
     * @return
     */
    public Key getKey(int keyId){
        for(int i  =0; i<slots.size;i++){
            if(slots.get(i).getObject() instanceof  Key){
                if(keyId == ((Key) slots.get(i).getObject()).getKeyId()){
                    return ((Key) slots.get(i).getObject());
                }
            }
        }
        return null;
    }

    /**
     * gets the crystal key based on id
     * @param id
     * @return
     */
    public CrystalPiece getCrystalPiece(int id){
        for(int i  =0; i<slots.size;i++){
            if(slots.get(i).getObject() instanceof CrystalPiece){
                if(id == ((CrystalPiece) slots.get(i).getObject()).getCrystalId()){
                    return ((CrystalPiece) slots.get(i).getObject());
                }
            }
        }
        return null;
    }

    /**
     * stores the object and the amount you want to store
     * @param object
     * @param amount
     * @return
     */
    public boolean store(WorldObject object, int amount) {

        // first check for a slot with the same object type
        Slot itemSlot = firstSlotWithItem(object);
        if (itemSlot != null) {

            itemSlot.add(object, amount);
            return true;
        } else {

            // now check for an available empty slot
            Slot emptySlot = firstSlotWithItem(null);
            if (emptySlot != null) {
                emptySlot.add(object, amount);
                return true;
            }
        }

        // no slot to add
        return false;
    }

    /**
     * drops an object from the inventory
     * @param object
     * @return
     */
    public boolean drop(WorldObject object){
       for(int i = 0;i<slots.size;i++){
           if(slots.get(i).getObject() == object){
               slots.get(i).take(1);

           }
       }
        return false;
    }
    public void dropCrystals(CrystalPiece piece){
        for(int i = 0;i<slots.size;i++){
            if(slots.get(i).getObject() instanceof CrystalPiece){
                slots.get(i).take(1);

            }
        }

    }

    /**
     * return the inv slots
     */
    public Array<Slot> getSlots() {
        return slots;
    }


    private Slot firstSlotWithItem(WorldObject object) {
        for (Slot slot : slots) {
            if (slot.getObject() == object) {
                return slot;
            }
        }

        return null;
    }

    /**
     *
     * @param weapon to unequip
     * @return weapon to equip
     */
    public Object SwapWeapon(Weapon weapon){
        store(weapon, 1);
        for(Slot slot : slots){
            if(slot.getObject() instanceof Weapon){
                Weapon weap = (Weapon)slot.getObject();
                drop(weap);
                return weap;
            }
        }
        return null;
    }

    /**
     * to string for inventory
     * @return
     */
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < slots.size; i++) {
            str += slots.get(i) + " ";
        }

        return str;
    }

}

