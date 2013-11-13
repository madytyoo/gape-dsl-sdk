angular.module('gape', ['ui.bootstrap','ngResource'])
	.factory("ToDos", function($resource) {
		return $resource('/rest/1.0/todos/:id/?offset=:offset&limit=:limit', { id:'@id', offset: '@offset', limit: '@limit'  })
	})
	.factory("ToDosDependencies", function($resource) {
		return $resource('/rest/1.0/todos/:id/dependencies', { id:'@id' })
	})
	.factory("ToDosSimilarTo", function($resource) {
		return $resource('/rest/1.0/todos/:id/similarTo', { id:'@id' })
	})
	.factory("ToDosEligible", function($resource) {
		return $resource('/rest/1.0/todos/:id/eligible', { id:'@id' })
	})	
	.controller('ToDosCtrl', function ($scope,ToDos,ToDosDependencies,ToDosSimilarTo) {

		$scope.calendar = undefined;
		$scope.alerts = [ { type: "info" , msg: "Data Loaded" ,icon:"icon-info-sign"} ]
		/* Paging Offset */
		$scope.offset = 0
		$scope.currentPage = 1;

		/* Initialize the tab */
		$scope.tabs = [{ id: "welcome", title:"About", page:"partials/Welcome.tpl.html" , icon: "icon-home" }];
		$scope.navType = 'pills';

		/* Detects if a tab is active */
		function isActive (id) {
			for(var i = 0;i < $scope.tabs.length; i++) {
				if($scope.tabs[i].id == id ) {
					return i;
				}
			}
			return -1			
		}
		
		$scope.selectPage = function(page) {
			$scope.currentPage = page;
			$scope.offset = (page -1) * 10;
			 $scope.loading = true;
			 setTimeout( function() {
				 $scope.todos = ToDos.query({ offset:$scope.offset , limit:10 }, function() {
					 $scope.loading = false;
				 });
			 }, 100); 
		}
		
		 $scope.notifyTab = function() {
            if( this.tab.id === "all" ) {
				 $scope.loading = true;
				 setTimeout( function() {
					 $scope.todos = ToDos.query({ offset:$scope.offset , limit:10 }, function() {
						 $scope.loading = false;
					 });
				 }, 100); 
			 } else {
				 var id = this.tab.id;
				 if(  !isNaN(id) ) {
		        	$scope.todo = ToDos.get({ id: id , offset:0, limit:10 } , function() {
	        			$scope.dependencies = ToDosDependencies.query( { id: id  }, function() {
	        				$scope.similar = ToDosSimilarTo.query( { id: id } );
	    				});
	        		});
				 }
			 }
		 }

		 /* All ToDos Action */
		$scope.allToDos = function () {
	    	var active = isActive("all")			
			if( active > -1) {		
		        $scope.tabs[active].active = true;
			} else {
				$scope.loading = true;
				setTimeout(function() {
					$scope.todos = ToDos.query({ offset:0, limit:10 },function() {
						$scope.loading = false;					
					});
				}, 10);
				 /* Pagination */								
				$scope.totalItems = 30;				
				$scope.maxSize = 5;
				$scope.tabs.push({ id: "all", title: " ToDos ", page:"partials/ToDos.tpl.html" , icon: "icon-tasks" , active: true });			
			}
	    }
		/* welcome */ 
		 /* All ToDos Action */
		$scope.welcome = function () {
			var active = isActive("welcome")			
			if( active > -1) {		
				$scope.tabs[active].active = true;
			} else {
				$scope.tabs.push( { id: "welcome", title:"About", page:"partials/Welcome.tpl.html" , icon: "icon-info" , active: true  } );
			}
		}

		$scope.specification = function () {
			$scope.tabs.push( { id: "spec", title:"Language Specification", page:"partials/specification.tpl.html" , active: true  } );
		}
		
		/* Open ToDo */
		$scope.openToDo = function (id,title) {
			$scope.alerts.splice(0, 1);
			$scope.alerts.push({type: "info", msg: "Loading", icon: "icon-download" });
			var active = isActive(id)
			if( active > -1) {
				$scope.todo = ToDos.get({ id: id , offset:0, limit:10 } , function() {
					$scope.tabs[active].active = true;
					$scope.dependencies = ToDosDependencies.query( { id: id }, function() {
						$scope.similar = ToDosSimilarTo.query( { id: id } );
							$scope.alerts.splice(0, 1);
							$scope.alerts.push({type: "info", msg: "Data Loaded", icon:"icon-info-sign"});
						}
					);
				});
			} else {
				$scope.todo = ToDos.get({ id: id, offset:0 , limit:10} ,
						function() { $scope.tabs.push(angular.copy( { id: id, title: " " + title + " ", page:"partials/CustomView.tpl.html" ,icon: "icon-tasks" , active: true } ));
							$scope.dependencies = ToDosDependencies.query( { id: id } , function() {
								$scope.similar = ToDosSimilarTo.query( { id: id } );
								$scope.alerts.splice(0, 1);
								$scope.alerts.push({type: "info", msg: "Data Loaded", icon:"icon-info-sign"});				    	  
							});
				});
			}
		}

		$scope.removeTab = function removeTab(tab) {
			var index = $scope.tabs.indexOf(tab);
			$scope.tabs.splice(index, 1);
		}

	})
	/* ToDo Controller */
	.controller('ToDoCtrl', function ($scope,$modal,$http,ToDos,ToDosDependencies,ToDosSimilarTo,ToDosEligible) {
		
		/* Alert */
		$scope.errors = new Array();

		
		$scope.getCss = function(ngModelContoller,name) {
		    return {
		      error: ngModelContoller.$invalid && ngModelContoller.$dirty && $scope.errors[name] !== undefined,
		      success: ngModelContoller.$valid && ngModelContoller.$dirty
		    };
		  };
		  
		  
		$scope.update = function () {			 
	        $scope.todo.$save(
	        	function() { 
	        		/* Success */
	        		$scope.alerts.splice(0, 1);
	        		$scope.alerts.push({type: "success", msg: "Data saved", icon:"icon-ok"});
	        		$scope.errors = { 	}
	        		$scope.todos = ToDos.query();
	        		$scope.similar = ToDosSimilarTo.query( { id: $scope.todo.id } );
	        	}, 
	        	function(response) { 
	        		/* Error */
	        		$scope.errors = response.data;
	        		$scope.alerts.splice(0, 1);
	        		$scope.alerts.push({type: "error", msg: "Error occurred" , icon: "icon-remove" });
	        	}	        		
	        );
	    }

		$scope.complete = function () {
			var config = {
					headers:  { 'Content-Type': 'application/json' }
			};
			
			$http.post("/rest/1.0/todos/" + $scope.todo.id + "/complete", config)
		      .success(function(data, status) {
	        		$scope.alerts.splice(0, 1);
	        		$scope.alerts.push({type: "success", msg: "Completed", icon:"icon-thumbs-up" });
	        		$scope.todo = ToDos.get({ id: $scope.todo.id , offset:0 } )
		      })
		      .error(function(data, status) {
	        		$scope.alerts.splice(0, 1);
	        		$scope.alerts.push({type: "error", msg: "Error occurred" , icon: "icon-remove" });
	        		

		    });
	        
	    }
		
		$scope.incomplete = function () {
			var config = {
					headers:  { 'Content-Type': 'application/json' }
			};
			
			$http.post("/rest/1.0/todos/" + $scope.todo.id + "/incomplete", config)
		      .success(function(data, status) {
	        		$scope.alerts.splice(0, 1);
	        		$scope.alerts.push({type: "success", msg: "Not Completed", icon: "icon-thumbs-down"});
	        		$scope.todo = ToDos.get({ id: $scope.todo.id , offset:0 } )
		      })
		      .error(function(data, status) {
	        		$scope.alerts.splice(0, 1);
	        		$scope.alerts.push({type: "error", msg: "Error occurred", icon: "icon-remove"});
		    });
	        
	    }
		
		$scope.addDependency = function () { 
			 $scope.eligible = ToDosEligible.query({ id: $scope.todo.id } );
			 
			 var modalInstance = $modal.open(
					 { templateUrl: '/partials/addDependency.tpl.html'  ,
					   controller: AddDependencyCtrl,
					   resolve: {
					        todos: function () {
					          return $scope.eligible;
					        }
					   }
					 } );
			 
			 modalInstance.result.then(function (toAdd) {
				 var config = {
							headers:  { 'Content-Type': 'application/json' }
					};
					
					$http.post("/rest/1.0/todos/" + $scope.todo.id + "/dependencies/" + toAdd, config)
				      .success(function(data, status) {
			        		$scope.alerts.splice(0, 1);
			        		$scope.alerts.push({type: "success", msg: "Dependency added", icon:"icon-ok" });				    	  
			        		$scope.dependencies = ToDosDependencies.query( { id: $scope.todo.id } )
				      })
				      .error(function(data, status) {
		        		$scope.alerts.splice(0, 1);
		        		$scope.alerts.push({type: "error", msg: data , icon:"icon-remove " });				    	  

				    });
				  
			 });
			 
		}
		
		$scope.removeDependency = function (toRemove) {
			var config = {
					headers:  { 'Content-Type': 'application/json' }
			};
			
			$http.delete("/rest/1.0/todos/" + $scope.todo.id + "/dependencies/" + toRemove, config)
		      .success(function(data, status) {
	        		$scope.alerts.splice(0, 1);
	        		$scope.alerts.push({ type: "success", msg: "Dependency removed", icon:"icon-ok" });				    	  
		    	  $scope.dependencies = ToDosDependencies.query( { id: $scope.todo.id } )
		      })
		      .error(function(data, status) {
	        		$scope.alerts.splice(0, 1);
	        		$scope.alerts.push({ type: "error", msg: "Error removing the Dependency", icon:"icon-remove" });				    	  
		    });
	        
	    }

		$scope.showError = function (name) {			 
	        return true;
	    }


	})
var AddDependencyCtrl =  function($scope, $modalInstance, todos ) {
		
	  $scope.todos = todos;
	  $scope.selected = 0;

	  $scope.select = function (id) {
		  $scope.selected = id;
	  };

	  $scope.ok = function () {
	    $modalInstance.close($scope.selected);
	  };

	  $scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	  };
	
}

	


