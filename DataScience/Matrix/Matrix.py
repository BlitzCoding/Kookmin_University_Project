class Matrix:
    def __init__(self, lst):
        self.data = lst

    def __repr__(self):
        return f"Matrix({self.data})"

    def __add__(self, other):
        A = self.data
        B = other.data
        answer = []
        for i in range(len(A)):
            tmp = []
            for j in range(len(A[i])):
                tmp.append(A[i][j] + B[i][j])
            answer.append(tmp)
        return Matrix(answer)

    def __sub__(self, other):
        A = self.data
        B = other.data
        answer = []
        for i in range(len(A)):
            tmp = []
            for j in range(len(A[i])):
                tmp.append(A[i][j] - B[i][j])
            answer.append(tmp)
        return Matrix(answer)

    def __mul__(self, other):
        answer = []
        if isinstance(other, int):
            A = self.data
            for i in range(len(A)):
                tmp = []
                for j in range(len(A[i])):
                    tmp.append(A[i][j] * other)
                answer.append(tmp)
            return Matrix(answer)
        else:
            A = self.data
            B = other.data

            X = len(A)
            Y = len(B[0])

            answer = []
            for i in range(X):
                tmp = []
                for j in range(Y):
                    num = 0
                    for k in range(len(A[0])):
                        A_num = A[i][k]
                        B_num = B[k][j]
                        num += A_num * B_num
                    tmp.append(num)
                answer.append(tmp)
            return Matrix(answer)

    __rmul__ = __mul__
