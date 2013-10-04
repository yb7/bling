Ext.define('Bling.controller.Navigation', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.Navigation'],
    views: ['foundation.CompanyMgmtGrid',
        'wms.ReceivingOrderMgmtGrid', 'wms.RegionalAllocationGrid',
        'wms.WarehouseMgmtGrid', 'wms.DistributionAllocationGrid',
        'wms.OutwardProcessingGrid', 'wms.PriceAdjustmentGrid',
				'wms.SellOrderMgmtGrid','wms.PreOrderMgmtGrid',
				'wms.CustomOrderMgmtGrid','wms.FixOrderMgmtGrid',
				'wms.CostOrderMgmtGrid','wms.CounterOrderMgmtGrid',
				'wms.MemberMgmtGrid',
        'sys.ScheduleGrid'],

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
        var xtype = record.get('id');

        if (xtype) { // only leaf nodes have ids
            this.application.fireEvent('addtocontentpanel', xtype);
        }
    }
});

