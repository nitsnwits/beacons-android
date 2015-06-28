package cmpe295.sjsu.edu.salesman;

/**
 * Created by Rucha on 6/28/15.
 * This class will handle the error response from backend.
 */
public class RestError {

    public String status;
    public int errorCode;
    public String errorMessage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
