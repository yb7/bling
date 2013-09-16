Ext.define('Bling.controller.Navigation', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.Navigation'],
    views: ['foundation.CompanyMgmtGrid',
        'wms.ReceivingOrderMgmtGrid', 'wms.RegionalAllocationGrid',
        'wms.WarehouseMgmtGrid', 'wms.DistributionAllocationGrid',
        'wms.OutwardProcessingGrid'],

    refs: [
        {
            selector: 'viewport',
            ref: 'viewport'
        },
        {
            selector: 'navigation',
            ref: 'navigation'
        },
        {
            selector: 'content-panel',
            ref: 'contentPanel'
        }
    ],

    init: function() {
        this.control({
            'navigation': {
                itemclick: this.onNavSelectionChange
            }
        })
    },

    onNavSelectionChange: function(view, record, item, index, e, eOpts) {
//        var record = records[0];
//            text = record.get('text'),
        var xtype = record.get('id');
//            alias = 'widget.' + xtype,
//            contentPanel = this.getContentPanel(),
//            cmp;

        if (xtype) { // only leaf nodes have ids
            this.application.fireEvent('addtocontentpanel', xtype);
//            contentPanel.removeAll(true);
//
//            var className = Ext.ClassManager.getNameByAlias(alias);
//            var ViewClass = Ext.ClassManager.get(className);
//
//            cmp = new ViewClass();
//            contentPanel.add(cmp);
//
////            contentPanel.setTitle(text);
//
//            document.title = document.title.split(' - ')[0] + ' - ' + text;
//            location.hash = xtype;
        }
    }
});

