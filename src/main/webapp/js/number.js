function numberOnkeyup(value) {
		var nwVal = "";
		if (value.match(/^\d{12}$/)) {
			nwVal = value.replace(value, parseInt(value / 12));
			nwVal = value.replace(/\.\d*\./g, '."');
			return nwVal;
		} else {
			return nwVal;
		}
	}

	function numberOnKeyPress(event, value) {
		if ((event.keyCode<48|| event.keyCode>57) && event.keyCode != 46
				&& event.keyCode != 45 || value.match(/^\d{12}$/)
				|| /\.\d{12}$/.test(value)) {
			event.returnValue = false;
			return;
		}
		if (value.indexOf('.') != -1 && event.keyCode == 46) {
			event.returnValue = false;
			return;
		}
		if (value.indexOf('.') != -1) {
			var numberlst = value.split(".");
			if (numberlst[1].length > 3) {
				event.returnValue = false;
				return;
			}
		}
	}
	
	
	
	   function keyPress(ob) {
		 if (!ob.value.match(/^[\+\-]?\d*?\.?\d*?$/)) ob.value = ob.t_value; else ob.t_value = ob.value; if (ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/)) ob.o_value = ob.value;
		}
		function keyUp(ob) {
		 if (!ob.value.match(/^[\+\-]?\d*?\.?\d*?$/)) ob.value = ob.t_value; else ob.t_value = ob.value; if (ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/)) ob.o_value = ob.value;
		        }
		function onBlur(ob) {
		if(!ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))ob.value=ob.o_value;else{if(ob.value.match(/^\.\d+$/))ob.value=0+ob.value;if(ob.value.match(/^\.$/))ob.value=0;ob.o_value=ob.value};
		}