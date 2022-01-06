<?php
    class DBConnect{
        private $con;
        
        function __construct(){

        }
        function connect(){
            include 'Connection.php';
            $this -> con = new mysqli($host,$user,$pass,$db);
            if(mysqli_connect_errno()){
                echo 'Failed to Connect to Database ';
            }
            return $this -> con;
        }
    }
?>