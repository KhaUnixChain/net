var host_ = "http://localhost:8080/rest";
var number = document.getElementById("number");
var username = document.getElementById("username");
var account_ = document.getElementById("username_");

var id = account_.innerHTML;
var app = angular.module("app", []);
number.innerHTML = (localStorage.getItem(id) == undefined) ? 0 : JSON.parse(localStorage.getItem(id)).length;

function getDateNow() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth();
    var day = date.getDay();
    var strDate = year + "-" + month + "-" + day;
    return new Date(strDate);
};

app.controller("history-ctrl", ($scope, $http) => {
    $scope.histories = [];
    $scope.selectId = [];

    $scope.load_histories = () => {
        var url = `${host_}/history/all`;
        $http.get(url).then((resp) => {
            $scope.histories = resp.data;
        }).catch((err) => {
            console.log("Error load histories", err);
        });
    };

    $scope.add = (id) => {
        var index = $scope.selectId.findIndex(item => item == id);
        if (index == -1) {
            $scope.selectId.push(id);
        }
        else {
            $scope.selectId.splice(index, 1);
        }
    };

    $scope.remove = (id) => {
        var url = `${host_}/history/${id}`;
        $http.delete(url).then((resp) => {
            var index = $scope.histories.findIndex(item => item.id == id);
            $scope.histories.splice(index, 1);
            console.log("Xoa thanh cong " + id, resp);
        });
    };

    $scope.delete = () => {
        if ($scope.selectId.length > 0) {
            $scope.selectId.forEach(id => {
                var url = `${host_}/history/${id}`;
                $http.delete(url).then((resp) => {
                    var index = $scope.histories.findIndex(item => item.id == id);
                    $scope.histories.splice(index, 1);
                    $scope.all = false;
                });
            });
        }
        else {
            alert("Choose some history to delete");
        }
    };

    $scope.choose_all = () => {
        if ($scope.all) {
            $scope.histories.forEach(h => {
                $scope.selectId.push(h.id);
            });
        }
        else {
            $scope.selectId = [];
        }
        console.log($scope.selectId.length);
    };

    $scope.load_histories();
});


app.controller('chart-ctrl', ($scope, $http) => {
	$scope.titles = [];
	$scope.load_title = () => {
		var url = `${host_}/categories/name/`;
		$http
			.get(url)
			.then((resp) => {
				$scope.titles = resp.data;
				var ctx = document.getElementById('myChart');
				var myChart = new Chart(ctx, {
					type: 'line',
					data: {
						labels: $scope.titles,
						datasets: [
							{
								label: 'Categories',
								data: [
									12, 19, 34, 5, 2, 3, 12, 19, 29, 5, 2, 3, 44, 12, 26, 3, 5,
									19, 16, 25, 33, 17,
								],
								backgroundColor: [
									'rgba(255, 99, 132, 0.2)',
									'rgba(54, 162, 235, 0.2)',
									'rgba(255, 206, 86, 0.2)',
									'rgba(75, 192, 192, 0.2)',
								],
								borderColor: [
									'rgba(255, 99, 132, 1)',
									'rgba(54, 162, 235, 1)',
									'rgba(255, 206, 86, 1)',
									'rgba(75, 192, 192, 1)',
								],
								borderWidth: 1,
							},
						],
					},
				});
			})
			.catch((err) => {
				console.log('Error load items', err);
			});
	};

	$scope.load_title();
});


app.controller('chart2-ctrl', ($scope, $http) => {
	$scope.titles = [];
	$scope.load_title = () => {
		var url = `${host_}/categories/name/`;
		$http
			.get(url)
			.then((resp) => {
				$scope.titles = resp.data;
				var ctx = document.getElementById('myChart2');
				var myChart = new Chart(ctx, {
					type: 'line',
					data: {
						datasets: [
							{
								label: 'Sales',
								data: [
									{
										x: '2022-01-01 00:00:00',
										y: 50555,
									},
									{
										x: '2022-06-06 00:00:00',
										y: 60333,
									},
									{
										x: '2022-12-30 23:59:59',
										y: 20444,
									},
								],
								borderColor: ['rgba(54, 162, 235, 1)'],
							},
						],
					},
				});
			})
			.catch((err) => {
				console.log('Error load items', err);
			});
	};

	$scope.load_title();
});


app.controller("category-ctrl", ($scope, $http) => {
    $scope.categories = [];
    $scope.index = 0;
    $("#prev").hide();

    $scope.load_categories = () => {
        var url = `${host_}/categories`;
        $http.get(url).then((resp) => {
            $scope.categories = resp.data;
        }).catch((err) => {
            console.log("Error load items", err);
        });
    };
    
    $scope.prev = () => {
        $scope.index = 0;
        $("#next").show();
        $("#prev").hide();
    };

    $scope.next = () => {
        $scope.index = 1;
        $("#next").hide();
        $("#prev").show();
    };


    $scope.load_categories();
});


// cái này là để hiển thị danh sách sản phẩm và thanh toán luôn
app.controller("checkout-ctrl", ($scope, $http) => {
    var id = account_.innerHTML;

    $scope.qty = 1;

    $scope.cart = {
        items: [],

        minus(id_product) {
            var item = this.items.find(item => item.id == id_product);
            item.qty = (item.qty < 2) ? 1 : item.qty - 1;
            this.saveToLocalStorage();
        },
    
        plus(id_product) {
            var item = this.items.find(item => item.id == id_product);
            item.qty++;
            this.saveToLocalStorage();
        },


        // Thêm sản phẩm vào giỏ hàng
        add(id_product){
            var item = this.items.find(item => item.id == id_product);
            if(item) {
                item.qty++;
                this.saveToLocalStorage();
            }
            else {
                $http.get(`${host_}/products/${id_product}`).then(resp => {
                    resp.data.qty = 1;
                    this.items.push(resp.data);
                    this.saveToLocalStorage();
                })
            }
        },

        // Xóa sản phẩm khỏi giỏ hàng
        remove(id_product){
            var index = this.items.findIndex(item => item.id == id_product);
            this.items.splice(index, 1);
            number.innerHTML -= 1;
            this.saveToLocalStorage();
        },

        // Xóa sạch các mặt hàng trong giỏ
        clear(){
            this.items = [];
            number.innerHTML = 0;
            this.saveToLocalStorage();
        },

        // Tính thành tiền của 1 sản phẩm
        amt_of(item) {
            return item.price * item.qty;
        },

        // Tính tổng số lượng các mặt hàng trong giỏ
        get count() {
            return this.items
                       .map(item => item.qty)
                       .reduce((total, qty) => total += qty, 0);
        },

        // Tổng thành tiền các mặt hàng trong giỏ
        get amount() {
            return this.items
                       .map(item => item.qty*item.price)
                       .reduce((total, qty) => total += qty,0);
        },

        // Lưu giỏ hàng vào local storage
        saveToLocalStorage() {
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem(id, json);
        },

        // Đọc giỏ hàng từ local storage
        loadFromLocalStorage() {
            var json = localStorage.getItem(id);
            this.items= json ? JSON.parse(json):[];
        }
    }
    $scope.cart.loadFromLocalStorage();

    $scope.order = {
        createDate: new Date(),
        address:"",
        account:{username: $("#username_").text()},
        get orderDetails(){
            return $scope.cart.items.map(item => {
                return {
                    product:{id: item.id},
                    price: item.price,
                    quantity: item.qty
                }
            });
        },
        purchase(){
            var order = angular.copy(this);
            // Thực hiện đặt hàng
            $http.post("/rest/orders", order).then(resp => {
                alert("Order Success!");
                $scope.cart.clear();
                location.href="/order/detail/" + resp.data.id;
            }).catch(error => {
                alert("Order error!")
                console.log(error)
            })
        }
    }
});


// cart là cái chỉ để thêm sản phẩm vào giỏ hàng
app.controller("cart-ctrl", ($scope, $http) => {

    // luôn lấy dữ liệu từ trong localStorage để khi bấm F5 ko bị mất
    var id = account_.innerHTML;
    number.innerHTML = (localStorage.getItem(id) == undefined) ? 0 : JSON.parse(localStorage.getItem(id)).length;
    $scope.letter = [0,1,2,3,4,5,6,7,8,9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23];

    $scope.qty = 1;

    $scope.minus = () => {
        $scope.qty = ($scope.qty < 2) ? 1 : $scope.qty-1;
    };

    $scope.plus = () => {
        $scope.qty++;
    };


    $scope.cart = {
        item: [],

        add(id_product) {
            var item = this.items.find(item => item.id == id_product);
            if (item) {
                item.qty += $scope.qty;
                this.saveToLocalStorage();
            }
            else {
                $http.get(`${host_}/products/${id_product}`).then(resp => {
                    resp.data.qty = $scope.qty;
                    this.items.push(resp.data);
                    this.saveToLocalStorage();
                    number.innerHTML = Number(number.innerHTML) + 1;   // để mặc định number sẽ là $scope.count (+1) để hiện 1 ngay - nó sẽ là localStorage đang giữ bao nhiêu item, nên ko nhất định là 1;
                })
            }
        },

        //Lưu giỏ hàng vào local storage
        saveToLocalStorage(){
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem(id, json);
        },

        //Đọc giỏ hàng từ local storage
        loadFromLocalStorage(){
            var json = localStorage.getItem(id);
            this.items = json ? JSON.parse(json) : [];
        }
    }

    $scope.cart.loadFromLocalStorage();
});


app.controller("product-ctrl", ($scope, $http) => {
    $scope.form = {};
    $scope.items = [];
    $scope.open = 0;

    $scope.load_product = () => {
        var url = `${host_}/products`;
        $http.get(url).then((resp) => {
            $scope.items = resp.data;
            console.log("load items product ok. ", resp);
        }).catch((err) => {
            console.log("Error load items", err);
        });
    };


    $scope.edit = (id) => {
        var url = `${host_}/products/${id}`;
        $http.get(url).then((resp) => {
            $scope.form = resp.data;
            $scope.open = 1;
            console.log("edit ok", resp);
        }).catch((err) => {
            console.log("edit fail", err);
        });
    };

    $scope.create = () => {
        var url = `${host_}/products`;
        var item = angular.copy($scope.form);

        item.available = (item.number == 0) ? false : true;
        item.createDate = (item.createDate == null) ? getDateNow(): item.createDate;
        item.image = document.getElementById("form-image").files[0].name;
        item.describe = NaN;


        $http.post(url, item).then((resp) => {
            $scope.items.push(item);
            $scope.load_product();
            console.log("Create success", resp);
        }).catch((err) => {
            console.log("error create", err);
        });
    };

    $scope.update = () => {
        var item = angular.copy($scope.form);
        var url = `${host_}/products/${$scope.form.id}`;
        $http.put(url, item).then((resp) => {
            var index = $scope.items.findIndex(item => item.id == $scope.form.id);
            $scope.items[index] = item;
            $scope.load_product();
            console.log("update success", resp);
        }).catch((err) => {
            console.log("Error", err);
        });
    };

    $scope.delete = (id) => {
        var item = angular.copy($scope.form);
        item.available = false;
        item.number = 0;
        var url = `${host_}/products/${id}`;
        $http.put(url, item).then((resp) => {
            console.log("delete success", resp);
            $scope.load_product();
        }).catch((err) => {
            console.log("error", err);
        });
    };

    $scope.reset = () => {
        $scope.form = {"price": 0, "number": 0};
        $scope.open = 0;
    };



    $scope.sum_seconds_food = 48*60*60;
    

    $scope.load_product();
});


app.controller("order-ctrl", ($scope, $http) => {
    $scope.orders = [];
    $scope.orderdetails = [];
    $scope.bill = {};
    $scope.sum = 0;

    // load all of order
    $scope.load_orders = () => {
        var url_order = `${host_}/orders`;
        $http.get(url_order).then((resp) => {
            $scope.orders = resp.data;
            console.log("load order", resp);
        }).catch((err) => {
            console.log("error order", err);
        });
    };

    // load all of detail
    $scope.load_orderdetails = () => {
        var url_orderdetails = `${host_}/orderdetails`;
        $http.get(url_orderdetails).then((resp) => {
            $scope.orderdetails = resp.data;
            console.log("load order detail", resp);
        }).catch((err) => {
            console.log("error order detail", err);
        });
    };

    // edit order to show product and qua
    $scope.edit = (id) => {
        var url_orderdetails = `${host_}/details/order/${id}`;
        var s = 0;
        $http.get(url_orderdetails).then((resp) => {
            $scope.bill = resp.data;
            for (let index = 0; index < $scope.bill.length; index++) {
                s += Number($scope.bill[index].quantity) * Number($scope.bill[index].product.price);
            }
            $scope.sum = s;
            console.log("load form detail " + $scope.sum, resp);
        }).catch((err) => {
            console.log("error order detail", err);
        });
    };


    $scope.load_orders();
    $scope.load_orderdetails();
});


app.controller("keyword-ctrl", ($scope, $http) => {
    $scope.keysearch = "";
    $scope.keywords = [];

    var keywords_load = localStorage.getItem("keywords");
    if (keywords_load == undefined) {
        localStorage.setItem("keywords", JSON.stringify($scope.keywords));
    }
    else {
        $scope.keywords = JSON.parse(keywords_load);
    }


    // dang bi loi cho nay khi add va delete
    $scope.add = function () {
        var search_input = document.getElementById("search-gallery");
        search_input.addEventListener("keypress", function(event) {
            if (event.key === "Enter" && $scope.keysearch != "") {
                $scope.keywords.push($scope.keysearch);
                // this is create new set to access not duplicate data
                var set = new Set($scope.keywords);

                // this is convert Array from to Set before
                $scope.keywords = Array.from(set);

                // Save a array keywords after converted
                localStorage.setItem("keywords", JSON.stringify($scope.keywords));

                // go head a any page with URL + kw
                window.location.href = "http://localhost:8080/user/search?keyword=" +$scope.keysearch; 
            }
       });
    };

    $scope.delete = (kw) => {
        var index = $scope.keywords.findIndex(item => item == kw);
        $scope.keywords.splice(index, 1);
        localStorage.setItem("keywords", JSON.stringify($scope.keywords));
    };


    // -----------------------------------------------------------------------------------------------
    // do không thể dùng id của thymeleaf cho ng-click nên đổi category thành angular để lấy id
    $scope.categories = [];
    $scope.load_categories = () => {
        var url = `${host_}/categories`;
        $http.get(url).then((resp) => {
            $scope.categories = resp.data;
        }).catch((err) => {
            console.log("Error load items", err);
        });
    };
    $scope.load_categories();


    $scope.priceFrom = 0;
    $scope.priceTo   = 1000000;
    $scope.map_filter = {"rate": 0, "cateId": "", "priceFrom": $scope.priceFrom, "priceTo": $scope.priceTo};

    $scope.add_item_filter = (id) => {
        var item = String(id);
        var index = item.indexOf("-");
        var fStr = item.substring(0, index);
        var lstr = item.substring(index+1, item.length);
        $scope.map_filter[fStr] = lstr;
        console.log($scope.map_filter);
    };

    $scope.add_price_filter = () => {
        if ($scope.priceFrom < $scope.priceTo) {
            $scope.map_filter["priceFrom"] = $scope.priceFrom;
            $scope.map_filter["priceTo"] = $scope.priceTo;
        }
        console.log($scope.map_filter);
    };

    $scope.filter_submit = () => {
        var rate = Number($scope.map_filter["rate"]);
        var cateId = String($scope.map_filter["cateId"]);
        var priceFrom = Number($scope.map_filter["priceFrom"]);
        var priceTo = Number($scope.map_filter["priceTo"]);
        var url = `http://localhost:8080/user/filter?rate=${rate}&cateId=${cateId}&priceFrom=${priceFrom}&priceTo=${priceTo}`;
        window.location.href = url;
        console.log(url);
    };

});


app.controller("changed-ctrl", ($scope, $http) => {
    $scope.pass1 = "";
    $scope.pass2 = "";
    $scope.pass3 = "";
    $scope.error1 = "";
    $scope.error2 = "";
    $scope.error3 = "";

    $scope.change = () => {
        var id = account_.innerHTML;
        var url = `${host_}/accounts/${id}`;
        $http.get(url).then((resp) => {
            $scope.account = resp.data;

            $scope.error1 = ($scope.pass1 == "" || $scope.pass1 == NaN) ? "(*) Password hiện tại không được để trống." :
                            (!angular.equals($scope.pass1, $scope.account.password)) ? "(*) Password hiện tại không chính xác": "" ;
            
            $scope.error2 = ($scope.pass2 == "" || $scope.pass2 == NaN) ? "(*) Password mới không được để trống." : "" ;
            $scope.error3 = ($scope.pass3 == "" || $scope.pass3 == NaN) ? "(*) Xác nhận password không được để trống." : 
                            ($scope.pass2       != $scope.pass3) ? "(*) Mật khẩu xác nhận không trùng nhau."                 : "" ;
            

            if ($scope.error1 == "" && $scope.error2 == "" && $scope.error3 == "") {
                $scope.account.password = $scope.pass3;
                var item = angular.copy($scope.account);
                $http.put(url, item).then((resp) => {
                    alert("Đổi mật khẩu thành công", resp);
                }).catch((err) => {
                    alert("Đổi mật khẩu that bai", err);
                });
            }
        });
    };
});

app.controller("comment-ctrl", ($scope, $http) => {
    $scope.comment = {};
    var url = `${host_}/comments`;
    var item = angular.copy($scope.comment);
    $http.get(url, item).then((resp) => {
        console.log("Comment posted !", resp);
        window.location.href = "http://localhost:8080/user/detail/" +$scope.keysearch; 
    }).catch((err) => {
        console.log("Comment failed !", err);
    });
});

app.controller("detail-staff", ($scope, $http) => {
    var productId = $("#id_product_staff").val();
    $scope.productdetails = [];

    // false là mặc định chưa có gì trong info
    $scope.confirm = false;
    $http.get(`${host_}/product/detail/exist/${productId}`).then((resp) => {
        $scope.confirm = resp.data;
        console.log($scope.confirm);
    }).catch((err) => {
        $scope.confirm = false;
    });


    // add product detail
    var url = `${host_}/product/detail/${productId}`;
    $http.get(url).then((resp) => {
        $scope.productdetails = resp.data;
        console.log('load detail ok [detail-staff]', resp);
    }).catch((err) => {
        console.log('load detail off [detail-staff]', err);
    });

    $scope.add = () => {
        $scope.productdetails.forEach(item => {
            var productDetail = {};
            productDetail.categoryDetailId = item.categoryDetailId;
            productDetail.info = item.info;
            productDetail.productId = item.productId;
            productDetail = angular.copy(productDetail);

            if ($scope.confirm == false) {
                var path = `${host_}/product/detail`;
                $http.post(path, productDetail).then((resp) => {
                    console.log("Add thanh cong", resp);
                }).catch((err) => {
                    console.log("Add failed", err);
                });
            }
        });
        window.location.href = "http://localhost:8080/staff/detail/" + productId; 
    };


    $scope.update = () => {
        $scope.productdetails.forEach(item => {
            var productDetail = {};
            productDetail.id = item.id;
            productDetail.categoryDetailId = item.categoryDetailId;
            productDetail.info = item.info;
            productDetail.productId = item.productId;
            productDetail = angular.copy(productDetail);

            if ($scope.confirm == true) {
                var path = `${host_}/product/detail/update/${item.id}`;
                $http.put(path, productDetail).then((resp) => {
                    console.log("Update thanh cong", resp);
                }).catch((err) => {
                    console.log("Update failed", err);
                });
            }
        });
        window.location.href = "http://localhost:8080/staff/detail/" + productId; 
    };
});
