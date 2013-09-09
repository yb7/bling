Ext.define('Bling.Application', {
    name: 'Bling',

    extend: 'Ext.app.Application',

    views: [
        "Viewport",
        "Header",
        "Navigation",
        "ContentPanel"
    ],

    controllers: [
        'Navigation',
        'foundation.CompanyMgmtGrid'
    ],

    stores: [
        'foundation.Companies'
    ]
});
