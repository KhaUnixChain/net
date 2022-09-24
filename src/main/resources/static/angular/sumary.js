var host_ = 'http://localhost:8080/rest';
var number = document.getElementById('number');
var username = document.getElementById('username');
var app = angular.module('app', []);

number.innerHTML =
	localStorage.getItem('cart') == undefined
		? 0
		: JSON.parse(localStorage.getItem('cart')).length;

app.controller('category-ctrl', ($scope, $http) => {
	$scope.categories = [];
	$scope.index = 0;
	$('#prev').hide();

	$scope.load_categories = () => {
		var url = `${host_}/categories`;
		$http
			.get(url)
			.then((resp) => {
				$scope.categories = resp.data;
				console.log('load items categories ok. ');
			})
			.catch((err) => {
				console.log('Error load items', err);
			});
	};

	$scope.prev = () => {
		$scope.index = 0;
		$('#next').show();
		$('#prev').hide();
	};

	$scope.next = () => {
		$scope.index = 1;
		$('#next').hide();
		$('#prev').show();
	};

	$scope.load_categories();
});
//Chart  1
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
app.controller('employee-ctrl', ($scope, $http) => {
	$scope.employees = [];

	// get list employees from API
	$scope.load_employees = () => {
		var url = `${host_}/authorities/staff`;
		$http
			.get(url)
			.then((resp) => {
				$scope.employees = resp.data;
				console.log('load ok employee');
			})
			.catch((err) => {
				console.log('Error employee', err);
			});
	};

	$scope.load_employees();
});

app.controller('checkout-ctrl', ($scope) => {
	// luôn lấy dữ liệu từ trong localStorage để khi bấm F5 ko bị mất
	// $scope.count = (localStorage.getItem("cart") == undefined) ? 0 : JSON.parse(localStorage.getItem("cart")).length;

	// $scope.cart = {
	//     // this is an item present
	//     items: [],

	//     // this is function remove an item
	//     remove(id) {
	//         var index = this.items.findIndex(item => item.id == id);
	//         this.items.splice(index, 1);
	//         this.saveToLocalStorage();
	//     },

	//     // this is function clear all of items
	//     clear() {
	//         this.items = [];
	//         this.saveToLocalStorage();
	//         number.innerHTML = 0;
	//     },

	//     // calculate money for each item
	//     amt_of(item){
	//         return item.qty*item.price;
	//     },

	//     // sum items qty into cart, all qty of items
	//     get count(){
	//         return this.items
	//                    .map(item => item.qty)
	//                    .reduce((total, qty) => total += qty, 0);
	//     },

	//     // sum money all of items
	//     get amount(){
	//         return this.items
	//                    .map(item => item.qty * item.price)
	//                    .reduce((total, qty) => total += qty, 0);
	//     },

	//     // save cart on localstorage
	//     saveToLocalStorage(){
	//         var json = JSON.stringify(angular.copy(this.items));
	//         localStorage.setItem("cart", json);
	//     },

	//     // get cart from localstorage
	//     loadFromLocalStorage(){
	//         var json = localStorage.getItem("cart");
	//         this.items = json ? JSON.parse(json) : [];
	//     },

	//     // process payment
	//     payment() {
	//         this.clear();
	//         swal("Good job!", "You clicked the button!", "success");
	//         this.saveToLocalStorage();
	//     },
	// };

	// $scope.cart.loadFromLocalStorage();

	$scope.cart = {
		items: [],
		//Thêm sản phẩm vào giỏ hàng
		add(id) {
			var item = this.items.find((item) => item.id == id);
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			} else {
				$http.get(`/rest/products/${id}`).then((resp) => {
					resp.data.qty = 1;
					this.items.push(resp.data);
					this.saveToLocalStorage();
				});
			}
			alert('Added to cart!');
		},
		//Xóa sản phẩm khỏi giỏ hàng
		remove(id) {
			var index = this.items.findIndex((item) => item.id == id);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
		},
		//Xóa sạch các mặt hàng trong giỏ
		clear() {
			this.items = [];
			this.saveToLocalStorage();
		},
		//Tính thành tiền của 1 sản phẩm
		amt_of(item) {},
		//Tính tổng số lượng các mặt hàng trong giỏ
		get count() {
			return this.items
				.map((item) => item.qty)
				.reduce((total, qty) => (total += qty), 0);
		},
		//Tổng thành tiền các mặt hàng trong giỏ
		get amount() {
			return this.items
				.map((item) => item.qty * item.price)
				.reduce((total, qty) => (total += qty), 0);
		},
		//Lưu giỏ hàng vào local storage
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.items));
			localStorage.setItem('cart', json);
		},
		//Đọc giỏ hàng từ local storage
		loadFromLocalStorage() {
			var json = localStorage.getItem('cart');
			this.items = json ? JSON.parse(json) : [];
		},
	};
	$scope.cart.loadFromLocalStorage();

	$scope.order = {
		createDate: new Date(),
		address: '',
		account: { username: $('#username').text() },
		get orderDetails() {
			return $scope.cart.items.map((item) => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty,
				};
			});
		},
		purchase() {
			var order = angular.copy(this);
			//Thực hiện đặt hàng
			$http
				.post('/rest/orders', order)
				.then((resp) => {
					alert('Order Success!');
					$scope.cart.clear();
					location.href = '/order/detail/' + resp.data.id;
				})
				.catch((error) => {
					alert('Order error!');
					console.log(error);
				});
		},
	};
});

app.controller('cart-ctrl', ($scope, $http) => {
	// luôn lấy dữ liệu từ trong localStorage để khi bấm F5 ko bị mất
	$scope.count =
		localStorage.getItem('cart') == undefined
			? 0
			: JSON.parse(localStorage.getItem('cart')).length;

	$scope.status = {
		qty_js: 1,

		minus() {
			this.qty_js--;
		},
		plus() {
			this.qty_js++;
		},
	};

	$scope.cart = {
		// this is an item present
		items: [],

		// this is function add an item
		add(id) {
			var item = this.items.find((item) => item.id == id);
			if (item) {
				item.qty += $scope.status.qty_js;
				this.saveToLocalStorage();
			} else {
				$http.get(`${host_}/products/${id}`).then((resp) => {
					resp.data.qty = $scope.status.qty_js; // lấy sty_js ban đầu được chọn
					this.items.push(resp.data);
					this.saveToLocalStorage();
					number.innerHTML = $scope.count + 1; // để mặc định number sẽ là $scope.count (+1) để hiện 1 ngay - nó sẽ là localStorage đang giữ bao nhiêu item, nên ko nhất định là 1;
				});
			}
			number.innerHTML = $scope.count + 1; // cập nhật bao nhiêu loại hàng trong cart (not qty)
		},

		// save cart on localstorage
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.items));
			localStorage.setItem('cart', json);
		},

		// get cart from localstorage
		loadFromLocalStorage() {
			var json = localStorage.getItem('cart');
			this.items = json ? JSON.parse(json) : [];
		},
	};

	$scope.cart.loadFromLocalStorage();
});

app.controller('product-ctrl', ($scope, $http) => {
	$scope.form = {};
	$scope.items = [];
	$scope.open = 0;

	$scope.load_product = () => {
		var url = `${host_}/products`;
		$http
			.get(url)
			.then((resp) => {
				$scope.items = resp.data;
				console.log('load items product ok. ');
			})
			.catch((err) => {
				console.log('Error load items', err);
			});
	};

	$scope.edit = (id) => {
		var url = `${host_}/products/${id}`;
		$http
			.get(url)
			.then((resp) => {
				$scope.form = resp.data;
				$scope.open = 1;
				console.log('edit ok', resp);
			})
			.catch((err) => {
				console.log('edit fail', err);
			});
	};

	$scope.create = () => {
		var url = `${host_}/products`;
		var item = angular.copy($scope.form);
		$http
			.post(url, item)
			.then((resp) => {
				$scope.items.push(item);
				$scope.load_product();
				console.log('Create success', resp);
			})
			.catch((err) => {
				console.log('error create', err);
			});
	};

	$scope.update = () => {
		var item = angular.copy($scope.form);
		var url = `${host_}/products/${$scope.form.id}`;
		$http
			.put(url, item)
			.then((resp) => {
				var index = $scope.items.findIndex((item) => item.id == $scope.form.id);
				$scope.items[index] = item;
				$scope.load_product();
				console.log('update success', resp);
			})
			.catch((err) => {
				console.log('Error', err);
			});
	};

	$scope.delete = (id) => {
		var url = `${host_}/products/${id}`;
		$http
			.delete(url)
			.then((result) => {
				var index = $scope.items.findIndex((item) => item.id == $scope.form.id);
				$scope.items.splice(index, 1);
				$scope.clear();
				console.log('delete success', result);
			})
			.catch((err) => {
				console.log('error', err);
			});
	};

	$scope.reset = () => {
		$scope.form = { price: 0, number: 0 };
		$scope.open = 0;
	};

	$scope.sum_seconds_food = 48 * 60 * 60;

	$scope.load_product();
});

app.controller('order-ctrl', ($scope, $http) => {
	$scope.orders = [];
	$scope.orderdetails = [];
	$scope.bill = {};
	$scope.sum = 0;

	// load all of order
	$scope.load_orders = () => {
		var url_order = `${host_}/orders`;
		$http
			.get(url_order)
			.then((resp) => {
				$scope.orders = resp.data;
				console.log('load order', resp);
			})
			.catch((err) => {
				console.log('error order', err);
			});
	};

	// load all of detail
	$scope.load_orderdetails = () => {
		var url_orderdetails = `${host_}/orderdetails`;
		$http
			.get(url_orderdetails)
			.then((resp) => {
				$scope.orderdetails = resp.data;
				console.log('load order detail', resp);
			})
			.catch((err) => {
				console.log('error order detail', err);
			});
	};

	// edit order to show product and qua
	$scope.edit = (id) => {
		var url_orderdetails = `${host_}/details/order/${id}`;
		var s = 0;
		$http
			.get(url_orderdetails)
			.then((resp) => {
				$scope.bill = resp.data;
				for (let index = 0; index < $scope.bill.length; index++) {
					s +=
						Number($scope.bill[index].quantity) *
						Number($scope.bill[index].product.price);
				}
				$scope.sum = s;
				console.log('load form detail ' + $scope.sum, resp);
			})
			.catch((err) => {
				console.log('error order detail', err);
			});
	};

	$scope.load_orders();
	$scope.load_orderdetails();
});

app.controller('keyword-ctrl', ($scope, $http) => {
	$scope.id_last = 0;
	$scope.keywords = [];
	$scope.set_keyword = { id: 0, keysearch: '', account: {} };

	$scope.loadKeyword = () => {
		var url = `${host_}/keywords/account/${username.innerHTML}`;
		$http.get(url).then((resp) => {
			$scope.keywords = resp.data;
		});

		var url_acc_ = `${host_}/accounts/${username.innerHTML}`;
		$http.get(url_acc_).then((resp) => {
			$scope.set_keyword.account = resp.data;
		});

		var url_last_id = `${host_}/keywords/lastid/${username.innerHTML}`;
		$http.get(url_last_id).then((resp) => {
			$scope.id_last = Number(resp.data);
		});
	};

	// dang bi loi cho nay khi add va delete
	$scope.add = () => {
		var search_input = document.getElementById('search-gallery');
		search_input.addEventListener('keypress', function (event) {
			if (event.key === 'Enter') {
				var url = `${host_}/keywords`;
				$scope.set_keyword.id = $scope.id_last + 1;
				var item = angular.copy($scope.set_keyword);
				$http
					.post(url, item)
					.then((resp) => {
						$scope.keywords.push($scope.set_keyword);
						$scope.clear();
						console.log('> add success keyword ', resp);
					})
					.catch((err) => {
						console.log('error add keyword ', err);
					});

				$('.menu-search').hide();
			}
		});
	};

	$scope.delete = (id) => {
		var url = `${host_}/keywords/${id}`;
		$http
			.delete(url)
			.then((resp) => {
				var index = $scope.keywords.findIndex((item) => item.id == id);
				$scope.keywords.splice(index, 1);
				$scope.clear();
				console.log('> delete success keyword ', resp);
			})
			.catch((err) => {
				console.log('error delete key ave id ' + id, err);
			});
	};

	$scope.clear = () => {
		$scope.set_keyword = { id: 0, keysearch: '', account: {} };
		$scope.loadKeyword();
	};

	$scope.loadKeyword();
});
