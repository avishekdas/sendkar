const Client = require('node-rest-client').Client;
var client = new Client();
var url = require('url');

/**
 * GET /download
 * Contact form page.
 */
exports.getDownload = (req, res) => {
  const unknownUser = !(req.user);

  res.render('download', {
    title: 'Download',
    unknownUser,
  });
};

exports.getDocList = (req, res) => {
	var query = url.parse(req.url,true).query;
	var string = JSON.stringify(query);
	var objectValue = JSON.parse(string);
	
	//console.log(objectValue['receivermobilenumber']);
	if(objectValue['receivermobilenumber'] === '') {
		const errors = 'Receipient Mobile number missing';
		if (errors) {
			req.flash('errors', errors);
			return res.redirect('/download');
		}
	}
  
  	//Call api
	var docurl = "http://172.31.6.104:8083/api/download/getdocumentlist/";
    docurl = docurl + objectValue['receivermobilenumber'];
	 
	client.get(docurl, function (data, response) {
		var resources = [];
		for(var i = 0; i < data.length; i++) {
			//console.log(data[i].filename);
			var resource = new Object();
			resource.id = data[i].id;
			resource.filename = data[i].filename;
			resources.push(resource);		
		};
		res.send(JSON.stringify(resources));
	});
};

exports.searchDoc = (req, res) => {
	var query = url.parse(req.url,true).query;
	var string = JSON.stringify(query);
	var objectValue = JSON.parse(string);
	
	//console.log(objectValue['receivermobilenumber']);
	if(objectValue['receivermobilenumber'] === '') {
		const errors = 'Receipient Mobile number missing';
		if (errors) {
			req.flash('errors', errors);
			return res.redirect('/download');
		}
	}
  
  	//Call api
	var docurl = "http://172.31.6.104:8083/api/download/getdocumentlist/";
    docurl = docurl + objectValue['receivermobilenumber'];
	 
	client.get(docurl, function (data, response) {
		var resources = [];
		for(var i = 0; i < data.length; i++) {
			//console.log(data[i].filename);
			var resource = new Object();
			resource.id = data[i].id;
			resource.filename = data[i].filename;
			resources.push(resource);		
		};
		res.send(JSON.stringify(resources));
	});
};

exports.getDocument = (req, res) => {
	var query = url.parse(req.url,true).query;
	var string = JSON.stringify(query);
	var objectValue = JSON.parse(string);
	
	if(objectValue['docid'] === '') {
		const errors = 'Receipient Mobile number missing';
		if (errors) {
			req.flash('errors', errors);
			return res.redirect('/download');
		}
	}
    
  	//Call api
	var docurl = "http://172.31.6.104:8083/api/download/assisted/file/";
    docurl = docurl + objectValue['docid'];
    docurl = docurl + "/" + objectValue['otp'];
	client.get(docurl, function (data, response) {
        var fileName;
        var contenttype = response.headers['content-type'];
        var contentdisposition = response.headers['content-disposition']
        var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
        var matches = filenameRegex.exec(contentdisposition);
        if (matches != null && matches[1]) {
            fileName = matches[1].replace(/['"]/g, '');
        }
        
		res.setHeader('Content-Type',contenttype);
		res.setHeader("Content-Disposition","attachment;filename="+fileName);
		res.end(data,'binary');
	});
};

exports.getOtp = (req, res) => {
	var query = url.parse(req.url,true).query;
	var string = JSON.stringify(query);
	var objectValue = JSON.parse(string);
	
	if(objectValue['docid'] === '') {
		const errors = 'Receipient Mobile number missing';
		if (errors) {
			req.flash('errors', errors);
			return res.redirect('/download');
		}
	}
  
  	//Call api
	var docurl = "http://172.31.6.104:8083/api/download/getdocdownloadotp/";
    docurl = docurl + objectValue['docid'];
	 
	client.get(docurl, function (data, response) {
        //console.log(data);
        res.send(data);
	});
};

/**
 * POST /download
 */
exports.postDownload = (req, res) => {
  if (!req.user) {
    req.assert('receivermobilenumber', 'Mobile # cannot be blank').notEmpty();
  }

  const errors = req.validationErrors();

  if (errors) {
    req.flash('errors', errors);
    return res.redirect('/download');
  }
  
  	//Call api
	var docurl = "http://172.31.6.104:8083/api/download/getdocumentlist/";
    docurl = docurl + req.body.receivermobilenumber;
	 
	client.get(docurl, function (data, response) {
		//console.log(data);
		//console.log(response);
		var resources = [];
		data.forEach(function(doc) {
			//console.log(doc.filename);
			var resource = new Object();
			resource.id = doc.id;
			resource.filename = doc.filename;
			resources.push(resource);		
		});
		res.send(JSON.stringify(resources));
	});
};
