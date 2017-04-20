/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;

import Data.Unit;
import Gateway.UnitGateway;

/**
 *
 * @author GanitGenius
 */
public class UnitSetup {
    
    UnitGateway unitGateway = new UnitGateway();
    
    public Object save(Unit unit){
        if(unitGateway.isUniqName(unit)){
            unitGateway.save(unit);
        }
        return unit;
    }

    public Object delete(Unit unit){
        if(unitGateway.isNotUse(unit)){
            unitGateway.delete(unit);
        }
        return unit;
    }
}
