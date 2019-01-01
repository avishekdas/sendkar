const Client = require('node-rest-client').Client;
var client = new Client();
var url = require('url');

exports.postSearch = (req, res) => {
	var query = url.parse(req.url,true).query;
	var string = JSON.stringify(query);
	var objectValue = JSON.parse(string);
	
	console.log(string);
    
    var args = {
		data: {
			id : objectValue['id'],
			uploadername : objectValue['uploadername'],
			filename : objectValue['filename'],
			sendermobilenumber : objectValue['sendermobilenumber'],
			receivermobilenumber: objectValue['receivermobilenumber'],
            senderaddress : objectValue['senderaddress'],
			receiveraddress : objectValue['receiveraddress'],
			message: objectValue['message']
		},
		headers: { "Content-Type": "application/json" }
	};
  
  	//Call api
	var docurl = "http://172.31.6.104:8083/api/download/search";
     
	client.get(docurl, args, function (data, response) {
		var resources = [];
		for(var i = 0; i < data.length; i++) {
			console.log(data[i]);
		};
		res.send(JSON.stringify(resources));
	});
};
