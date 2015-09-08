package com.tankbattle.data;

import android.os.Environment;

import com.tankbattle.model.Genre;
import com.tankbattle.model.Nation;
import com.tankbattle.model.Tank;
import com.tankbattle.service.TankService;

/**
 * Created by olivier on 08/09/15.
 */
public class InitDataBase {

    public static void initTankList() {

        TankService.deleteAllTanks();

        String DEFAULT_IMAGE = Environment.getExternalStorageDirectory() + "/TankBattle/defaultTank.jpg";

        // Russe
        Tank tank = new Tank("KV-1", 10, Nation.RU, Genre.HEAVY, DEFAULT_IMAGE); tank.save();
        tank = new Tank("KV-2", 10, Nation.RU, Genre.HEAVY, DEFAULT_IMAGE); tank.save();
        tank = new Tank("T-55", 10, Nation.RU, Genre.HEAVY, DEFAULT_IMAGE); tank.save();
        tank = new Tank("T-34/76", 10, Nation.RU, Genre.MEDIUM, DEFAULT_IMAGE); tank.save();
        tank = new Tank("ISu 152", 10, Nation.RU, Genre.DESTROYER, DEFAULT_IMAGE); tank.save();
        tank = new Tank("Su-85", 10, Nation.RU, Genre.DESTROYER, DEFAULT_IMAGE); tank.save();

        // Allemand
        tank = new Tank("Tigre 1", 10, Nation.DE, Genre.HEAVY, DEFAULT_IMAGE); tank.save();
        tank = new Tank("Tigre 1 (Fin de production)", 10, Nation.DE, Genre.HEAVY, DEFAULT_IMAGE); tank.save();
        tank = new Tank("Ferdinand", 10, Nation.DE, Genre.HEAVY, DEFAULT_IMAGE); tank.save();
        tank = new Tank("Jagdpanther", 10, Nation.DE, Genre.DESTROYER, DEFAULT_IMAGE); tank.save();
        tank = new Tank("Jagdtiger (Henschel)", 10, Nation.DE, Genre.DESTROYER, DEFAULT_IMAGE); tank.save();
        tank = new Tank("Sturmgeschutz III Ausf.E Sturmgeschu", 10, Nation.DE, Genre.DESTROYER, DEFAULT_IMAGE); tank.save();
        tank = new Tank("Sdkfz 138 Marder III Ausf M", 10, Nation.DE, Genre.DESTROYER, DEFAULT_IMAGE); tank.save();
        tank = new Tank("LE FH18 Auf39-H 10.5cm", 10, Nation.DE, Genre.ARTILLERY, DEFAULT_IMAGE); tank.save();

        // Anglais
        tank = new Tank("Churchill MK.III", 10, Nation.EN, Genre.HEAVY, DEFAULT_IMAGE); tank.save();
        tank = new Tank("AS-90 SPG", 10, Nation.EN, Genre.ARTILLERY, DEFAULT_IMAGE); tank.save();

        // Americain
        tank = new Tank("M1A1 - IFOR", 10, Nation.US, Genre.HEAVY, DEFAULT_IMAGE); tank.save();
        tank = new Tank("M4A3 Sherman", 10, Nation.US, Genre.MEDIUM, DEFAULT_IMAGE); tank.save();
        tank = new Tank("M4A1 Sherman", 10, Nation.US, Genre.MEDIUM, DEFAULT_IMAGE); tank.save();

        // Francais
        tank = new Tank("B1 bis", 10, Nation.FR, Genre.HEAVY, DEFAULT_IMAGE); tank.save();


//
//                  Cromwell + Auto + Moderne
    }
}
