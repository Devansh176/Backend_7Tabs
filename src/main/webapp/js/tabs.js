function showTab(tabId) {
    var contents = document.querySelectorAll('.content');
    for(var i=0; i<contents.length; i++) {
        contents[i].style.display = 'none';
    }

    var selectedTab = document.getElementById(tabId);
    if(selectedTab) {
        selectedTab.style.display = 'block';
    }

    if(tabId==='tab3' && typeof window.initTab3 === 'function') {
        window.initTab3();
    }

    if(tabId==='tab4' && typeof window.initTab4 === 'function') {
        window.initTab4();
    }
}
