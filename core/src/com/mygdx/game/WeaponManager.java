package com.mygdx.game;

/**
 * Created by Hp on 11/12/2559.
 */
public class WeaponManager {
    int weaponindex,arsenal;
    double ammo[],maxammo[];
    boolean isuseammo[];
    public WeaponManager(int arsenal){
        weaponindex=0;this.arsenal=arsenal;
        ammo= new double[arsenal];
        maxammo=new double[arsenal];
        isuseammo=new boolean[arsenal];
    }

    public void changeweaponright(){weaponindex=(weaponindex>=this.arsenal-1)?0:weaponindex+1;}
    public void changeweaponleft(){weaponindex=(weaponindex<=0)?this.arsenal-1:weaponindex-1;}



}
