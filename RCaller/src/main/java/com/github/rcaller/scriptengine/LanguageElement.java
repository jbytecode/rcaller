
package com.github.rcaller.scriptengine;


public class LanguageElement {
    
    private String objectName;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
    
    public LanguageElement(String objectName){
        this.objectName = objectName;
    }
    
}
