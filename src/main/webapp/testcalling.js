
function TestCaller(url, testObjects, testListNode, statusNode, traceNode) {
    this.testObjects = testObjects;
    this.testListNode = testListNode;
    this.statusNode = statusNode;
    this.traceNode = traceNode;
    this.url = url;
}

TestCaller.prototype.startCalling = function() {
    this.statusNode.innerHTML = 'Processing...';
    this.addTest(this.testObjects[0]);
    this.ajaxCall(0);
};

TestCaller.prototype.handleRequest = function(testIndex, request) {
    if (request.readyState === 4) {
        if (request.status === 200) {
            this.setSucceeded(testIndex);
            testIndex++;
            if(testIndex < this.testObjects.length) {
                this.addTest(this.testObjects[testIndex]);
                this.ajaxCall(testIndex);
            } else {
                this.statusNode.innerHTML = 'Success!';
                this.statusNode.style = 'color:green';
            }
        } else if (request.status === 409) {
            this.setFailed(testIndex, request.responseText);
            this.statusNode.innerHTML = 'Fail!';
            this.statusNode.style = 'color:red';
        }
    }
};

TestCaller.prototype.setSucceeded = function(testIndex) {
    this.testObjects[testIndex].node.style = 'color:green';
};

TestCaller.prototype.setFailed = function(testIndex, trace) {
    this.testObjects[testIndex].node.style = 'color:red';
    this.traceNode.innerHTML = trace;
};

TestCaller.prototype.addTest = function(test) {
    var testNode = document.createElement('P');
    testNode.innerHTML = test.name;
    test.node = this.testListNode.appendChild(testNode);
};

TestCaller.prototype.ajaxCall = function(testIndex) {
    var request = new XMLHttpRequest();
    var thisCaller = this;
    request.onreadystatechange = function() {
        thisCaller.handleRequest(testIndex, request);
    };
    request.open('GET', this.url + '?class=' + this.testObjects[testIndex].className, true);
    request.send();
};