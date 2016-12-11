package com.mygdx.game;

/**
 * Created by Hp on 11/12/2559.
 */
public class WeaponManager {
    int weaponindex,arsenal;
    public WeaponManager(int arsenal){
        weaponindex=0;this.arsenal=arsenal;
    }

    public void changeweaponright(){weaponindex=(weaponindex>=this.arsenal)?0:weaponindex+1;}
    public void changeweaponleft(){weaponindex=(weaponindex<=0)?this.arsenal:weaponindex-1;}



}
