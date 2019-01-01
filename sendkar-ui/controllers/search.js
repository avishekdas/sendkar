const Client = require('node-rest-client').Client;
var client = new Client();
var url = require('url');

/**
 * GET /download
 * Contact form page.
 */
exports.getSearch = (req, res) => {
  const unknownUser = !(req.user);

  res.render('search', {
    title: 'Search',
    unknownUser,
  });
};

exports.getSearchDoc = (req, res) => {
	var query = url.parse(req.url,true).query;
	var string = JSON.stringify(query);
	var objectValue = JSON.parse(string);
	
	console.log(string);
    
    var args = {
		data: {
			"id" : objectValue['id'],
			"filename" : objectValue['filename']
		},
		headers: { "Content-Type": "application/json" }
	};
  
  	//Call api
	var docurl = "http://172.31.6.104:8083/api/download/search";
    
    console.log(args);
    
	client.post(docurl, args, function (data, response) {
        console.log(data);
		var resources = [];
		for(var i = 0; i < data.length; i++) {
			console.log(data[i]);
		};
		res.send(JSON.stringify(resources));
	});
};
