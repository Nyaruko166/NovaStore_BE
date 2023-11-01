USE [master]
GO
/****** Object:  Database [Nova_Store_DB]    Script Date: 31/10/2023 22:26:05 PM ******/
CREATE DATABASE [Nova_Store_DB]
GO
USE [Nova_Store_DB]
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Nova_Store_DB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Nova_Store_DB] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ANSI_NULLS OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ANSI_PADDING OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ARITHABORT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET AUTO_CLOSE ON
GO
ALTER DATABASE [Nova_Store_DB] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [Nova_Store_DB] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [Nova_Store_DB] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [Nova_Store_DB] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [Nova_Store_DB] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [Nova_Store_DB] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [Nova_Store_DB] SET  ENABLE_BROKER
GO
ALTER DATABASE [Nova_Store_DB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [Nova_Store_DB] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [Nova_Store_DB] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [Nova_Store_DB] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [Nova_Store_DB] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [Nova_Store_DB] SET RECOVERY SIMPLE
GO
ALTER DATABASE [Nova_Store_DB] SET  MULTI_USER
GO
ALTER DATABASE [Nova_Store_DB] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [Nova_Store_DB] SET DB_CHAINING OFF
GO
ALTER DATABASE [Nova_Store_DB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF )
GO
ALTER DATABASE [Nova_Store_DB] SET TARGET_RECOVERY_TIME = 60 SECONDS
GO
ALTER DATABASE [Nova_Store_DB] SET DELAYED_DURABILITY = DISABLED
GO
ALTER DATABASE [Nova_Store_DB] SET ACCELERATED_DATABASE_RECOVERY = OFF
GO
ALTER DATABASE [Nova_Store_DB] SET QUERY_STORE = ON
GO
ALTER DATABASE [Nova_Store_DB] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [Nova_Store_DB]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 10/10/2023 9:34:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [Email] [varchar](50) NULL,
    [PhoneNumber] [varchar](15) NULL,
    [Password] [varchar](255) NULL,
    [Birthday] [datetime] NULL,
    [Avatar] [varchar](255) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [RoleId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Address]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Address](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [SpecificAddress] [nvarchar](50) NULL,
    [Ward] [nvarchar](50) NULL,
    [District] [nvarchar](50) NULL,
    [City] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [AccountId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Bill]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Bill](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [CustomerName] [nvarchar](50) NULL,
    [Address] [nvarchar](100) NULL,
    [PhoneNumber] [nvarchar](15) NULL,
    [Note] [nvarchar](255) NULL,
    [OrderDate] [datetime] NULL,
    [PaymentDate] [datetime] NULL,
    [ConfirmationDate] [datetime] NULL,
    [ShippingDate] [datetime] NULL,
    [ReceivedDate] [datetime] NULL,
    [CompletionDate] [datetime] NULL,
    [ShippingFee] [money] NULL,
    [Price] [money] NULL,
    [DiscountAmount] [money] NULL,
    [TotalPrice] [money] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [AccountId] [int] NULL,
    [CustomerId] [int] NULL,
    [VoucherId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[BillDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[BillDetail](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Price] [money] NULL,
    [Quantity] [int] NULL,
    [Status] [int] NULL,
    [BillId] [int] NULL,
    [ProductDetailId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[BillHistory]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[BillHistory](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [Describe] [nvarchar](255) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [BillId] [int] NULL,
    [AccountId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Brand]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Brand](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Cart]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Cart](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [CreateDate] [datetime] NULL,
    [AccountId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[CartDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[CartDetail](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Price] [money] NULL,
    [Quantity] [int] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [CartId] [int] NULL,
    [ProductDetailId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Category]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Category](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Color]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Color](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Form]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Form](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Image]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Image](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [varchar](255) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [ProductDetailId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Material]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Material](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[PaymentMethod]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[PaymentMethod](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [Money] [money] NULL,
    [Description] [nvarchar](50) NULL,
    [Status] [int] NULL,
    [BillId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Product]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Product](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [Description] [nvarchar](255) NULL,
    [Price] [money] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [BrandId] [int] NULL,
    [CategoryId] [int] NULL,
    [FormId] [int] NULL,
    [MaterialId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[ProductDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[ProductDetail](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Quantity] [int] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [ProductId] [int] NULL,
    [SizeId] [int] NULL,
    [ColorId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Promotion]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Promotion](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Code] [varchar](255) NULL,
    [Name] [nvarchar](50) NULL,
    [Type] [bit] NULL,
    [Value] [money] NULL,
    [StartDate] [datetime] NULL,
    [EndDate] [datetime] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[PromotionDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[PromotionDetail](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [ProductId] [int] NULL,
    [PromotionId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Role]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Role](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Size]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Size](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Voucher]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Voucher](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Code] [varchar](50) NULL,
    [Name] [nvarchar](50) NULL,
    [Type] [bit] NULL,
    [Value] [money] NULL,
    [Quantity] [int] NULL,
    [MinimumPrice] [money] NULL,
    [MaximumDiscount] [money] NULL,
    [StartDate] [datetime] NULL,
    [EndDate] [datetime] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[Account]  WITH CHECK ADD  CONSTRAINT [FKi9xmahyh65di7x2wn5fvt8lv3] FOREIGN KEY([RoleId])
    REFERENCES [dbo].[Role] ([Id])
    GO
ALTER TABLE [dbo].[Account] CHECK CONSTRAINT [FKi9xmahyh65di7x2wn5fvt8lv3]
    GO
ALTER TABLE [dbo].[Address]  WITH CHECK ADD  CONSTRAINT [FK3haj5uqn2j6ar58mcglooa5bp] FOREIGN KEY([AccountId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Address] CHECK CONSTRAINT [FK3haj5uqn2j6ar58mcglooa5bp]
    GO
ALTER TABLE [dbo].[Bill]  WITH CHECK ADD  CONSTRAINT [FK5mrre5s0gacpqu6737kfocwkl] FOREIGN KEY([CustomerId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Bill] CHECK CONSTRAINT [FK5mrre5s0gacpqu6737kfocwkl]
    GO
ALTER TABLE [dbo].[Bill]  WITH CHECK ADD  CONSTRAINT [FKdlpt4y8nuurj83nht236onfi6] FOREIGN KEY([AccountId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Bill] CHECK CONSTRAINT [FKdlpt4y8nuurj83nht236onfi6]
    GO
ALTER TABLE [dbo].[Bill]  WITH CHECK ADD  CONSTRAINT [FK2hf3g6padqdy15tccpshmpxob] FOREIGN KEY([VoucherId])
    REFERENCES [dbo].[Voucher] ([Id])
    GO
ALTER TABLE [dbo].[Bill] CHECK CONSTRAINT [FK2hf3g6padqdy15tccpshmpxob]
    GO
ALTER TABLE [dbo].[BillDetail]  WITH CHECK ADD  CONSTRAINT [FK8sw1tfhht3q5xtdsyoe0r7jfd] FOREIGN KEY([BillId])
    REFERENCES [dbo].[Bill] ([Id])
    GO
ALTER TABLE [dbo].[BillDetail] CHECK CONSTRAINT [FK8sw1tfhht3q5xtdsyoe0r7jfd]
    GO
ALTER TABLE [dbo].[BillDetail]  WITH CHECK ADD  CONSTRAINT [FKnt7lacod5l24jdnfgxfydqiu2] FOREIGN KEY([ProductDetailId])
    REFERENCES [dbo].[ProductDetail] ([Id])
    GO
ALTER TABLE [dbo].[BillDetail] CHECK CONSTRAINT [FKnt7lacod5l24jdnfgxfydqiu2]
    GO
ALTER TABLE [dbo].[BillHistory]  WITH CHECK ADD  CONSTRAINT [FKdwtgkov1ar5uki43w7okh9xqi] FOREIGN KEY([BillId])
    REFERENCES [dbo].[Bill] ([Id])
    GO
ALTER TABLE [dbo].[BillHistory] CHECK CONSTRAINT [FKdwtgkov1ar5uki43w7okh9xqi]
    GO
ALTER TABLE [dbo].[BillHistory]  WITH CHECK ADD  CONSTRAINT [FKowiek2xkcm34kzm23jlzms92j] FOREIGN KEY([AccountId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[BillHistory] CHECK CONSTRAINT [FKowiek2xkcm34kzm23jlzms92j]
    GO
ALTER TABLE [dbo].[Cart]  WITH CHECK ADD  CONSTRAINT [FK1w1km3ww10t0maawf2cymyx5i] FOREIGN KEY([AccountId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Cart] CHECK CONSTRAINT [FK1w1km3ww10t0maawf2cymyx5i]
    GO
ALTER TABLE [dbo].[CartDetail]  WITH CHECK ADD  CONSTRAINT [FK6unelr9lsy26gw9da5tuuxcsh] FOREIGN KEY([CartId])
    REFERENCES [dbo].[Cart] ([Id])
    GO
ALTER TABLE [dbo].[CartDetail] CHECK CONSTRAINT [FK6unelr9lsy26gw9da5tuuxcsh]
    GO
ALTER TABLE [dbo].[CartDetail]  WITH CHECK ADD  CONSTRAINT [FKepuvpwbykpahqt0gagvvdqoyn] FOREIGN KEY([ProductDetailId])
    REFERENCES [dbo].[ProductDetail] ([Id])
    GO
ALTER TABLE [dbo].[CartDetail] CHECK CONSTRAINT [FKepuvpwbykpahqt0gagvvdqoyn]
    GO
ALTER TABLE [dbo].[Image]  WITH CHECK ADD  CONSTRAINT [FKdtaisglfgjjj5j1a7g3fcev7c] FOREIGN KEY([ProductDetailId])
    REFERENCES [dbo].[ProductDetail] ([Id])
    GO
ALTER TABLE [dbo].[Image] CHECK CONSTRAINT [FKdtaisglfgjjj5j1a7g3fcev7c]
    GO
ALTER TABLE [dbo].[PaymentMethod]  WITH CHECK ADD  CONSTRAINT [FKjity6x6p1194mtoli4abb3jgc] FOREIGN KEY([BillId])
    REFERENCES [dbo].[Bill] ([Id])
    GO
ALTER TABLE [dbo].[PaymentMethod] CHECK CONSTRAINT [FKjity6x6p1194mtoli4abb3jgc]
    GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK4cx1ir1xlnytlte2quullny9m] FOREIGN KEY([FormId])
    REFERENCES [dbo].[Form] ([Id])
    GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK4cx1ir1xlnytlte2quullny9m]
    GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK6pnobu31k3yhhmk45s97imkui] FOREIGN KEY([CategoryId])
    REFERENCES [dbo].[Category] ([Id])
    GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK6pnobu31k3yhhmk45s97imkui]
    GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FKfc8uiunvrolmn3qa9ahrhmtrw] FOREIGN KEY([MaterialId])
    REFERENCES [dbo].[Material] ([Id])
    GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FKfc8uiunvrolmn3qa9ahrhmtrw]
    GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FKnuw1iwpj73v904j79uc8qurgc] FOREIGN KEY([BrandId])
    REFERENCES [dbo].[Brand] ([Id])
    GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FKnuw1iwpj73v904j79uc8qurgc]
    GO
ALTER TABLE [dbo].[ProductDetail]  WITH CHECK ADD  CONSTRAINT [FKik38y3bjry9u05majdn5u3egj] FOREIGN KEY([ProductId])
    REFERENCES [dbo].[Product] ([Id])
    GO
ALTER TABLE [dbo].[ProductDetail] CHECK CONSTRAINT [FKik38y3bjry9u05majdn5u3egj]
    GO
ALTER TABLE [dbo].[ProductDetail]  WITH CHECK ADD  CONSTRAINT [FK61xkqx42jtcc8we64hahp05pv] FOREIGN KEY([SizeId])
    REFERENCES [dbo].[Size] ([Id])
    GO
ALTER TABLE [dbo].[ProductDetail] CHECK CONSTRAINT [FK61xkqx42jtcc8we64hahp05pv]
    GO
ALTER TABLE [dbo].[ProductDetail]  WITH CHECK ADD  CONSTRAINT [FKntlsi9s4irkogtc9mbw03s90y] FOREIGN KEY([ColorId])
    REFERENCES [dbo].[Color] ([Id])
    GO
ALTER TABLE [dbo].[ProductDetail] CHECK CONSTRAINT [FKntlsi9s4irkogtc9mbw03s90y]
    GO
ALTER TABLE [dbo].[PromotionDetail]  WITH CHECK ADD  CONSTRAINT [FKa83ktjk5axkasy5c9v2s1ukig] FOREIGN KEY([ProductId])
    REFERENCES [dbo].[Product] ([Id])
    GO
ALTER TABLE [dbo].[PromotionDetail] CHECK CONSTRAINT [FKa83ktjk5axkasy5c9v2s1ukig]
    GO
ALTER TABLE [dbo].[PromotionDetail]  WITH CHECK ADD  CONSTRAINT [FKos6ftbatvw4mk81km7xpkds5i] FOREIGN KEY([PromotionId])
    REFERENCES [dbo].[Promotion] ([Id])
    GO
ALTER TABLE [dbo].[PromotionDetail] CHECK CONSTRAINT [FKos6ftbatvw4mk81km7xpkds5i]
    GO
    USE [master]
    GO
ALTER DATABASE [Nova_Store_DB] SET  READ_WRITE
GO

USE Nova_Store_DB

SELECT * FROM Promotion
    INSERT INTO Promotion (code, name, Type, Value, StartDate, EndDate, CreateDate, UpdateDate, Status)
VALUES ('CDCMCM', 'Extremely deep discounts', 1, 200000, '2023-10-10', '2023-11-11', GETDATE(), GETDATE(), 1);
INSERT INTO Promotion (code, name, Type, Value, StartDate, EndDate, CreateDate, UpdateDate, Status)
VALUES ('UMLTK', 'Destruction discount', 1, 200000, '2023-10-10', '2023-11-11', GETDATE(), GETDATE(), 1);
INSERT INTO Promotion (code, name, Type, Value, StartDate, EndDate, CreateDate, UpdateDate, Status)
VALUES ('ADBTD', 'god of war discount', 1, 200000, '2023-10-10', '2023-11-11', GETDATE(), GETDATE(), 1);
INSERT INTO Promotion (code, name, Type, Value, StartDate, EndDate, CreateDate, UpdateDate, Status)
VALUES ('NCDB', 'Disgusting discount', 1, 200000, '2023-10-10', '2023-11-11', GETDATE(), GETDATE(), 1);
INSERT INTO Promotion (code, name, Type, Value, StartDate, EndDate, CreateDate, UpdateDate, Status)
VALUES ('123MASK', 'Lord of discounts', 1, 200000, '2023-10-10', '2023-11-11', GETDATE(), GETDATE(), 1);
INSERT INTO Promotion (code, name, Type, Value, StartDate, EndDate, CreateDate, UpdateDate, Status)
VALUES ('HIHI12', 'Discount combos', 1, 200000, '2023-10-10', '2023-11-11', GETDATE(), GETDATE(), 1);
INSERT INTO Promotion (code, name, Type, Value, StartDate, EndDate, CreateDate, UpdateDate, Status)
VALUES ('AS09J2K', 'Huge promotion', 1, 200000, '2023-10-10', '2023-11-11', GETDATE(), GETDATE(), 1);

SELECT * FROM Brand
    INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES ('Gucci', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES ('Chanel', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES ('Dior', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES ('Givenchy', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES ('Calvin Klein', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES ('Boss', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES ('Fendi', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES ('Balenciaga', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES ('Luis Vuition', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES ('Adidass', GETDATE(), GETDATE(), 1)


SELECT * FROM Category
    INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES ('Kaki', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES ('Jeans', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES ('Trouser', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES ('Polo', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES ('Boxer', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES ('Blazer', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES ('Vest', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES ('Hoodie', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES ('Sport', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES ('Jacket', GETDATE(), GETDATE(), 1)

SELECT * FROM Material
    INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES ('100% Nano', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES ('Cotton', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES ('Denim', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES ('Nylon', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES ('Viscose', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES ('Wool', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES ('Linen', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES ('Syntheic', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES ('Polyester', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES ('Cotton Spandex', GETDATE(), GETDATE(), 1)


SELECT * FROM Form
    INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES ('Slim', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES ('Slim Crop', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES ('Regular', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES ('Relax', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES ('Loose', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES ('Straight', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES ('Carrot', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES ('Skinny', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES ('Jogger', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES ('Cargo', GETDATE(), GETDATE(), 1)


SELECT * FROM Size
    INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES ('28', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES ('29', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES ('30', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES ('31', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES ('32', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES ('XXL', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES ('XL', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES ('L', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES ('S', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES ('M', GETDATE(), GETDATE(), 1)


SELECT * FROM Color
    INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES ('Black', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES ('Red', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES ('Blue', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES ('Green', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES ('Yellow', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES ('White', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES ('Grey', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES ('Purple', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES ('Brown', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES ('Orange', GETDATE(), GETDATE(), 1)