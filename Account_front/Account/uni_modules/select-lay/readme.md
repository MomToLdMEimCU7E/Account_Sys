## 插件使用方法：
	
 `<select-lay :value="tval" name="name" :options="datalist" @selectitem="selectitem"></select-lay>`
  
## 配置参数：
 
 属性名|类型|默认值|说明
 :--:|:--:|:--:|-
 value|String|""|默认展示的value值
 name|String|""|input的字段名
 zindex|Number|""|层级，默认999，防止多个组件一起使用时下拉栏穿透
 slabel|String|label|自定义列表中键值对关系，参考示例
 svalue|String|value|自定义列表中键值对关系，该值对应value，参考示例
 placeholder|String|请选择|无选项时展示的文字
 showplaceholder|Boolean|true|下拉时是否展示请选择按钮
 options|Array|无|数据列表
 disabled|Boolean|false|是否禁用

## 事件：

 事件名|说明|返回值
 :--:|:--:|-
 @selectitem|点击项目触发的事件|参数 (索引，具体项目)
 
## 说明：
 
 此插件依赖scss，请务必安装！！！

 
## 示例：
 
 ```
	 <template>
	 	<view class="content">
	 		<form @submit="formSubmit">
	 			<view class="item">通用写法：</view>
	 			<select-lay :zindex="1211" :value="tval" name="name" placeholder="请选择项目" :options="datalist"
	 				@selectitem="selectitem">
	 			</select-lay>
	 			<view style="height:40rpx"></view>
	 			<view class="item">禁用组件：</view>
	 			<select-lay :zindex="1111" :value="tval2" name="name2" placeholder="请选择项目2" :options="datalist2"
	 				:disabled="true">
	 			</select-lay>
	 			<view style="height:40rpx"></view>
	 			<view class="item">自定义数据索引对象：</view>
	 			<select-lay :value="tval3" name="name3" slabel="myname" svalue="myvalue" placeholder="请选择项目3"
	 				:options="datalist3" @selectitem="selectitem3">
	 			</select-lay>
				<view style="height:40rpx"></view>
				<view class="item">取消下拉默认展示的提醒按钮</view>
				<select-lay :value="tval4" name="name4" placeholder="请选择项目4" :showplaceholder="false" :options="datalist4" @selectitem="selectitem4">
				</select-lay>
	 			<button class="btn" form-type="submit">Submit</button>
	 		</form>
	 	</view>
	 </template>
	 
	 <script>
	 	export default {
	 		data() {
	 			return {
	 				//模拟数据列表
	 				datalist: [],
	 				//模拟初始数据
	 				tval: "value2",
	 				//模拟数据列表
	 				datalist2: [],
	 				//模拟初始数据
	 				tval2: "2value1",
	 				//模拟数据列表
	 				datalist3: [],
	 				//模拟初始数据
	 				tval3: "myvalue1",
					//模拟数据列表
					datalist4: [],
					//模拟初始数据
					tval4: "4value1"
	 			}
	 		},
	 		onReady() {
	 			this.datalist = [{
	 					label: "label1",
	 					value: "value1"
	 				},
	 				{
	 					label: "label2",
	 					value: "value2"
	 				},
	 				{
	 					label: "label3",
	 					value: "value3"
	 				}
	 			];
	 			this.datalist2 = [{
	 					label: "2label1",
	 					value: "2value1"
	 				},
	 				{
	 					label: "2label2",
	 					value: "2value2"
	 				},
	 				{
	 					label: "2label3",
	 					value: "2value3"
	 				}
	 			];
	 			this.datalist3 = [{
	 					myname: "我是myname1",
	 					myvalue: "myvalue1"
	 				},
	 				{
	 					myname: "我是myname2",
	 					myvalue: "myvalue2"
	 				},
	 				{
	 					myname: "我是myname3",
	 					myvalue: "myvalue3"
	 				},
	 				{
	 					myname: "我是myname4",
	 					myvalue: "myvalue4"
	 				}
	 			];
				this.datalist4 = [{
						label: "4label1",
						value: "4value1"
					},
					{
						label: "4label2",
						value: "4value2"
					},
					{
						label: "4label3",
						value: "4value3"
					}
				];
	 
	 		},
	 		methods: {
	 			formSubmit(e) {
	 				console.log('form发生了submit事件，携带数据为：' + JSON.stringify(e.detail.value))
	 			},
	 			selectitem(index, item) {
	 				console.log(item)
	 				if (index >= 0) {
	 					this.tval = item.value;
	 				} else {
	 					this.tval = ""
	 				}
	 			},
	 			selectitem3(index, item) {
	 				console.log(item)
	 				if (index >= 0) {
	 					this.tval3 = item.myvalue;
	 				} else {
	 					this.tval3 = ""
	 				}
	 			},
				selectitem4(index, item) {
					console.log(item)
					if (index >= 0) {
						this.tval4 = item.value;
					} else {
						this.tval4 = ""
					}
				}
	 		}
	 	}
	 </script>
 
 ```
 