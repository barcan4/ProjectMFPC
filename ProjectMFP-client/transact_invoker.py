import requests

header = "http://localhost:8087/transact"

class TransactInvoker:

    def get_cars(self):
        url = "/get_car"
        try:
            carList = requests.get(header + url).json()
            return carList
        except requests.exceptions.HTTPError as error:
            print(error)
        return []

    
    def get_trucks(self):
        url = "/get_truck"
        try:
            truckList = requests.get(header + url).json()
            return truckList
        except requests.exceptions.HTTPError as error:
            print(error)
        return []

    def delete_obj(self, idStr, className):
        url = "/delete_obj?idStr=" + idStr + "&className=" + className
        try:
            response = requests.delete(header + url)
            return response
        except requests.exceptions.HTTPError as error:
            print(error)

    def save_obj(self, className, params):
        url = "/save_obj?className=" + className + "&params=" + params
        try:
            response = requests.post(header + url)
            return response
        except requests.exceptions.HTTPError as error:
            print(error)