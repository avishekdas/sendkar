const nodemailer = require('nodemailer');

var fs = require('fs');
var request = require('request');
const http = require('http');
const Multipart = require('multi-part');
var multiparty = require('multiparty');

/**
 * GET /upload
 * Contact form page.
 */
exports.getUpload = (req, res) => {
  const unknownUser = !(req.user);

  res.render('upload', {
    title: 'Upload',
    unknownUser,
  });
};

/**
 * POST /upload
 * Send a contact form via Nodemailer.
 */
exports.postUpload = (req, res) => {
  let fromName;
  let fromEmail;
  req.assert('uploadername', 'Uploader name cannot be blank').notEmpty();
  req.assert('sendermobilenumber', 'Sender mobile is not valid').isMobilePhone();
  req.assert('receivermobilenumber', 'Recipient mobile is not valid').isMobilePhone();
  req.assert('senderaddress', 'Sender address cannot be blank').notEmpty();
  req.assert('receiveraddress', 'Recipient address cannot be blank').notEmpty();

  const errors = req.validationErrors();

  if (errors) {
    req.flash('errors', errors);
    return res.redirect('/upload');
  }
  
  var form = new multiparty.Form();

    form.on('part', function(formPart) {
        var contentType = formPart.headers['content-type'];

        var formData = {
            file: {
                value: formPart,
                options: {
                    filename: formPart.filename,
                    contentType: contentType,
                    knownLength: formPart.byteCount
                }
            }
        };

        request.post({
            url: 'http://13.232.119.17:8082/api/upload/assisted/file',
            formData: formData
        });
    });

    form.on('error', function(error) {
        console.log(error);
    });

    form.on('close', function() {
       res.send('received upload');
    });

    form.parse(req);
};