/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;

import Data.Supplier;
import Gateway.SupplierGateway;

/**
 *
 * @author GanitGenius
 */
public class SupplierSetup {
    
    SupplierGateway supplierGateway = new SupplierGateway();

    public void save(){
        
    }
    
    public Object delete(Supplier supplier){
        if(supplierGateway.isNotUse(supplier)){
            supplierGateway.delete(supplier);
        }
        return supplier;
    }
}
