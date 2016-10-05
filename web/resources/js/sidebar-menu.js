// This is static function because we are calling it directly on jQuery object ($).
// In .html code we define what is "menu" parameter.
$.sidebarMenu = function(menu) {
  var animationSpeed = 300;
  
  // When you click on <a> tag which is inside <li> tag call define function.
  $(menu).on('click', 'li a', function(e) {
    var $this = $(this);
	// Find next element that is on same leves as "$this".
	// It doesn't have to be same type. This tag contains submenus of
	// menu that we click.
    var checkElement = $this.next();
	// First we check if maybe some menu is open and close it. All menus that
	// contain inside parts that can be open have class ".treeview-menu" so we
	// search for tags with that class and check if they are visible (tag is visible
	// if it occupy some space on page).
    if (checkElement.is('.treeview-menu') && checkElement.is(':visible')) {
      checkElement.slideUp(animationSpeed, function() {
        checkElement.removeClass('menu-open');
      });
      checkElement.parent("li").removeClass("active");
    }

    //If the menus are not open.
    else if ((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))) {
      // Get the parent menu (here we search for parent that is <ul> tag type and then
	  // from that result set of tags we take only first tag). In this case "parent" variable
	  // represent whole side bar.
      var parent = $this.parents('ul').first();
      // First we check if some menu is open and close it.
      var ul = parent.find('ul:visible').slideUp(animationSpeed);
      // Then remove the menu-open class from the parent.
      ul.removeClass('menu-open');
      // Then we get tag that is <li> parent from <a> tag that we click previously (this is menu that we want to open).
      var parent_li = $this.parent("li");

      // Open the target menu.
      checkElement.slideDown(animationSpeed, function() {
		// Then add the "menu-open" class to target tag.
        checkElement.addClass('menu-open');
		// Then we first find any <li> tags that have "active" class and remove that class.
        parent.find('li.active').removeClass('active');
		// And then we add the class "active" to the <li> target tag.
        parent_li.addClass('active');
      });
    }
    // If this isn't a link, prevent the page from being redirected
    if (checkElement.is('.treeview-menu')) {
      e.preventDefault();
    }
  });
}
