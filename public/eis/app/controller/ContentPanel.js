Ext.define('Bling.controller.ContentPanel', {
    extend: 'Ext.app.Controller',
    refs: [
        {
            selector: 'content-panel',
            ref: 'contentPanel'
        }
    ],

    init: function() {
        this.application.on({
            addtocontentpanel: this.addToContentPanel,
            scope: this
        });
    },
    addToContentPanel: function(config) {
        if (Ext.isString(config)){
            config = {xtype: config};
        }
        Ext.applyIf(config, {singleton: true, createConfig: {closable:true}});


        var childTab = null;
        var neetToCreate = true;
        // only xtype model can use singleton
        if (Ext.isDefined(config.xtype) && config.singleton) {
            childTab = this.getContentPanel().child(config.xtype);
            isExisted = childTab != null;

            if (isExisted) {
                neetToCreate = false;
            }
        }
        if (neetToCreate) {
            childTab = config.view;
            if (!Ext.isDefined(childTab)) {
                var alias = 'widget.' + config.xtype;
                var className = Ext.ClassManager.getNameByAlias(alias);
                var ViewClass = Ext.ClassManager.get(className);

                childTab  = new ViewClass(config.createConfig);
            }

            this.getContentPanel().add(childTab);
        }
        this.getContentPanel().setActiveTab(childTab);
    }

});

